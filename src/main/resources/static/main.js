document.addEventListener('DOMContentLoaded', () => initDashboard());

async function initDashboard() {
    try {
        const user = await apiGet('/api/whoami');
        const isAdmin = user?.role === 'ROLE_ADMIN';
        document.getElementById('userInfo').textContent = user?.jmeno ?? '';

        if (isAdmin) {
            //pripad
            show('createPripadSection', 'assignSection');
            on('pripadForm', 'submit', addPripad);
            on('assignForm', 'submit', assignStraznik);


            show('createPodezrelySection', 'podezrelyAssignSection');
            on('podezrelyForm', 'submit', addPodezrely);
            on('podezrelyAssignForm', 'submit', assignPodezrely);

       //straz
            show('createStraznikSection');
            on('straznikForm', 'submit', addStraznik);
        }

        on('vyslechForm', 'submit', addVyslech);


        await Promise.all([
            loadPripady(isAdmin),
            loadPodezreli(isAdmin),
            loadVyslechy(isAdmin),
            loadStraznici(isAdmin),
        ]);
    } catch {
        location.href = '/login.html';
    }
}


function on(id, ev, fn) { document.getElementById(id)?.addEventListener(ev, fn); }
function show(...ids)   { ids.forEach(i => (document.getElementById(i).style.display = '')); }
function qs(sel)        { return document.querySelector(sel); }
const centerEmpty = (cols, txt) => `<tr><td colspan="${cols}" class="center">– ${txt} –</td></tr>`;
const toIntOrNull = (v) => (v === '' || v == null ? null : Number.parseInt(v, 10));

function formatDateTime(isoString) {
    if (!isoString) return '';
    try {
        const d = new Date(isoString);
        return new Intl.DateTimeFormat('cs-CZ', {
            day: '2-digit', month: '2-digit', year: 'numeric',
            hour: '2-digit', minute: '2-digit', second: '2-digit',
            hour12: false
        }).format(d).replace(/\u00A0/g, ' '); // NBSP -> mezera
    } catch {
        return isoString;
    }
}




async function apiGet(url) {
    const r = await fetch(url, { credentials: 'include' });
    if (!r.ok) throw new Error(await r.text().catch(() => 'GET error'));
    return r.status === 204 ? null : r.json();
}
async function apiDelete(url) {
    const r = await fetch(url, { method: 'DELETE', credentials: 'include' });
    if (!r.ok) throw new Error(await r.text().catch(() => 'DELETE error'));
}
async function apiPostJson(url, bodyObj) {
    const r = await fetch(url, {
        method: 'POST',
        credentials: 'include',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(bodyObj),
    });
    if (!r.ok) throw new Error(await r.text().catch(() => 'POST error'));
    return r.status === 204 ? null : r.json();
}
async function apiPut(url) {
    const r = await fetch(url, { method: 'PUT', credentials: 'include' });
    if (!r.ok) throw new Error(await r.text().catch(() => 'PUT error'));
    return r.status === 204 ? null : r.json();
}

function fail(msg = 'Operace selhala') { alert(msg); }

//případy
async function loadPripady(isAdmin) {
    try {
        const list = await apiGet('/api/pripady');
        renderPripady(list ?? [], isAdmin);
    } catch { fail('Nelze načíst případy'); }
}

function renderPripady(list, isAdmin) {
    const tbody = qs('#pripadyTable tbody');
    if (!tbody) return;
    tbody.innerHTML = list.length ? '' : centerEmpty(5, 'žádné případy');
    list.forEach((p) => tbody.insertAdjacentHTML('beforeend', `
    <tr>
      <td>${p.id}</td>
      <td>${p.nazev}</td>
      <td>${p.popis ?? ''}</td>
      <td>${p.straznik?.jmeno ?? '-'}</td>
      <td>${isAdmin ? `<button class="del" data-id="${p.id}" data-type="pripady">🗑️</button>` : ''}</td>
    </tr>
  `));
    if (isAdmin) {
        tbody.querySelectorAll('.del').forEach((b) => {
            b.onclick = async () => {
                if (!confirm('Opravdu smazat případ?')) return;
                try {
                    await apiDelete(`/api/pripady/${b.dataset.id}`);
                    loadPripady(true);
                } catch { fail('Mazání případu selhalo'); }
            };
        });
    }
}
async function addPripad(e) {
    e.preventDefault();
    const dto = Object.fromEntries(new FormData(e.target).entries());
    try {
        await apiPostJson('/api/pripady', dto);
        e.target.reset();
        loadPripady(true);
    } catch { fail(); }
}
async function assignStraznik(e) {
    e.preventDefault();
    const { pripadId, straznikId } = Object.fromEntries(new FormData(e.target).entries());
    try {
        await apiPut(`/api/pripady/${pripadId}/straznik/${straznikId}`);
        e.target.reset();
        loadPripady(true);
    } catch { fail(); }
}

//podezřelí
async function loadPodezreli(isAdmin) {
    try {
        const list = await apiGet('/api/podezreli');
        renderPodezreli(list ?? [], isAdmin);
    } catch { fail('Nelze načíst podezřelé'); }
}

function renderPodezreli(list, isAdmin) {
    const tbody = qs('#podezreliTable tbody');
    if (!tbody) return;
    tbody.innerHTML = list.length ? '' : centerEmpty(6, 'žádní podezřelí');

    list.forEach((z) => tbody.insertAdjacentHTML('beforeend', `
    <tr>
      <td>${z.id}</td>
      <td>${z.jmeno}</td>
      <td>${z.vek ?? ''}</td>
      <td>${z.popis ?? ''}</td>
      <td>${z.pripad?.nazev ?? '-'}</td>
      <td>${isAdmin ? `<button class="del" data-id="${z.id}" data-type="podezreli">🗑️</button>` : ''}</td>
    </tr>
  `));

    if (isAdmin) {
        tbody.querySelectorAll('.del').forEach((b) => {
            b.onclick = async () => {
                if (!confirm('Opravdu smazat podezřelého?')) return;
                try {
                    await apiDelete(`/api/podezreli/${b.dataset.id}`);
                    loadPodezreli(true);
                } catch { fail('Mazání podezřelého selhalo'); }
            };
        });
    }
}

async function addPodezrely(e) {
    e.preventDefault();
    const raw = Object.fromEntries(new FormData(e.target).entries());
    const dto = {
        jmeno: raw.jmeno ?? '',
        popis: raw.popis ?? null,
        vek: toIntOrNull(raw.vek),
    };
    try {
        await apiPostJson('/api/podezreli', dto);
        e.target.reset();
        loadPodezreli(true);
    } catch { fail(); }
}

async function assignPodezrely(e) {
    e.preventDefault();
    const { podezrelyId, pripadId } = Object.fromEntries(new FormData(e.target).entries());
    try {
        await apiPut(`/api/podezreli/${podezrelyId}/pripad/${pripadId}`);
        e.target.reset();
        loadPodezreli(true);
    } catch { fail(); }
}

//výslechy
async function loadVyslechy(isAdmin) {
    try {
        const list = await apiGet('/api/vyslechy');
        renderVyslechy(list ?? [], isAdmin);
    } catch { fail('Nelze načíst výslechy'); }
}

function renderVyslechy(list, isAdmin) {
    const tbody = qs('#vyslechyTable tbody');
    if (!tbody) return;
    tbody.innerHTML = list.length ? '' : centerEmpty(5, 'žádné výslechy');

    list.forEach((v) => tbody.insertAdjacentHTML('beforeend', `
    <tr>
      <td>${v.id}</td>
      <td>${formatDateTime(v.datum)}</td>
      <td>${v.zapis ?? ''}</td>
      <td>${v.podezrely?.jmeno ?? '-'}</td>
      <td>${isAdmin ? `<button class="del" data-id="${v.id}" data-type="vyslechy">🗑️</button>` : ''}</td>
    </tr>
  `));

    if (isAdmin) {
        tbody.querySelectorAll('.del').forEach((b) => {
            b.onclick = async () => {
                if (!confirm('Opravdu smazat výslech?')) return;
                try {
                    await apiDelete(`/api/vyslechy/${b.dataset.id}`);
                    loadVyslechy(true);
                } catch { fail('Mazání výslechu selhalo'); }
            };
        });
    }
}

async function addVyslech(e) {
    e.preventDefault();
    const { podezrelyId, zapis } = Object.fromEntries(new FormData(e.target).entries());
    const qsParams = new URLSearchParams({
        podezrelyId: String(podezrelyId ?? ''),
        zapis: String(zapis ?? ''),
    });
    try {
        await fetch(`/api/vyslechy?${qsParams}`, { method: 'POST', credentials: 'include' })
            .then((r) => { if (!r.ok) throw new Error(); });
        e.target.reset();
        loadVyslechy(true);
    } catch { fail('Nelze přidat výslech'); }
}

//strážnící
async function loadStraznici(isAdmin) {
    try {
        const list = await apiGet('/api/straznici');
        renderStraznici(list ?? [], isAdmin);
    } catch { fail('Nelze načíst strážníky'); }
}

function renderStraznici(list, isAdmin) {
    const tbody = qs('#strazniciTable tbody');
    if (!tbody) return;
    tbody.innerHTML = list.length ? '' : centerEmpty(6, 'žádní strážníci');

    list.forEach((s) => tbody.insertAdjacentHTML('beforeend', `
    <tr>
      <td>${s.id}</td>
      <td>${s.jmeno}</td>
      <td>${s.hodnost ?? ''}</td>
      <td>${s.vek ?? ''}</td>
      <td>${s.rasa ?? ''}</td>
      <td>${isAdmin ? `<button class="del" data-id="${s.id}" data-type="straznici">🗑️</button>` : ''}</td>
    </tr>
  `));

    if (isAdmin) {
        tbody.querySelectorAll('.del').forEach((b) => {
            b.onclick = async () => {
                if (!confirm('Opravdu smazat strážníka?')) return;
                try {
                    await apiDelete(`/api/straznici/${b.dataset.id}`);
                    loadStraznici(true);
                } catch { fail('Mazání strážníka selhalo'); }
            };
        });
    }
}

async function addStraznik(e) {
    e.preventDefault();
    const raw = Object.fromEntries(new FormData(e.target).entries());
    // Username/Password neposíláme
    const dto = {
        jmeno:   raw.jmeno ?? '',
        hodnost: raw.hodnost ?? null,
        vek:     toIntOrNull(raw.vek),
        rasa:    raw.rasa ?? null
    };

    try {
        await apiPostJson('/api/straznici', dto);
        e.target.reset();
        loadStraznici(true);
    } catch { fail(); }
}
