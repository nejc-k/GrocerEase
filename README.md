# GrocerEase - Aplikacija za mobilno platformo Android

## Table of Contents

- [Opis aplikacije](#opis-aplikacije)
- [Vzpostavitev projekta za razvoj](#vzpostavitev-projekta-za-razvoj)
- [Struktura projekta](#struktura-projekta)
- [Baza popdatkov](#baza-podatkov)
- [Zaledni del](#zaledni-del)
- [Še ne dokončane funkcionalnosti](#še-ne-dokončane-funkcionalnosti)
- [Skupina TeachSavvy](#skupina-techsavvy)

---

## Opis aplikacije

GrocerEase je mobilna aplikacija za platformo Android, namenjena ustvarjanju in upravljanju
nakupovalnih listkov. Uporabnikom omogoča, da preprosto organizirajo nakupovalne sezname,
beležijo potrebne izdelke, dodajo opombe ter izberejo lokacijo trgovine, v kateri želijo
izdelek kupiti. Aplikacija ponuja osnoven, pregleden vmesnik, ki omogoča enostaven dostop do
glavnih funkcij, kot so pregled,  dodajanje, urejanje in odstranjevanje obstoječih nakupovalnih
listkov, možnost dodajanja novih ter urejanje in odstranjevanje obstoječih izdelkov na teh
listkih in filtriranje izdelkov glede na lokacijo trgovine.

---

## Vzpostavitev projekta za razvoj

### Zahteve

- **Android Studio**: Verzija Ladybug (2024.2.1) ali novejša
- **Java Development Kit (JDK)**: Verzija 11 ali višja
- **Android SDK**: API Level 26 (Oreo) ali višji
- **Gradle**: Vgrajene nastavitve v `build.gradle`:
    - **Plugins**:
        - `kotlin("plugin.serialization")` verzija `1.9.0`
    - **Compile SDK**: 34
    - **Target SDK**: 34
    - **Min SDK**: 26
    - **View Binding**: Omogočeno
    - **JVM Target**: `1.8`

### Kloniranje projekta

1. **Kopiraj povezavo za kloniranje**

   **POVEZAVA:** https://github.com/RUPS-TechSavy/GrocerEase.git

2. **Uvozi projekt v android studio**
    - Odpri Android studio
    - V orodni vrstici izberi `File > New > Project from Version control`
    - V izbirnem seznamu "Version Control" izberi "GIT"
    - Kopirano povezavo iz koraka 1 prilepi v polje "URL"
3. **Sinhronizacija in izgradnja aplikacije**
   Android Studio vas bo opozoril, da sinhronizirate Gradle datoteke. Sprejmite prenos knjižnic od
   katerih je aplikacija odvisna (dependencies). Zgradite projekt s sledenjem `Build > Make Project`,
   da se prepričate, da se vse prevede pravilno.
4. **Zaženite aplikacijo**
   Povežite se na napravo ali zaženite vgrajeni emulator, nato pa uporabite `Run > Run 'app'`, da
   zaženete aplikacijo.

---

## Struktura projekta

### Opis strukture
**app/**
- **java/**
    - Vse `.java` ali `.kt` datoteke, organizirane glede na paketno strukturo (npr., aktivnosti, fragmenti, view modeli itd.)
- **res/**
    - **drawable/**
        - Slike, ikone in drugi grafični elementi
    - **layout/**
        - XML datoteke za postavitve (UI) posameznih aktivnosti
    - **raw/**
      -Json datoteke za hranjenje izdelkov v posameznih restavracijah
    - **values/**
        - **colors.xml** – definira barvne vrednosti
        - **strings.xml** – shranjuje besedilne nize
        - **styles.xml** – definira stile za aplikacijo
    - **mipmap/**
        - Ikone za različne velikosti zaslonov (npr., launcher ikone)
- **AndroidManifest.xml**
    - Glavna konfiguracijska datoteka aplikacije

**gradle/**
- Konfiguracijske datoteke za Gradle

**build/**
- Vsebuje generirane datoteke po gradnji aplikacije

**.idea/**
- Nastavitvene datoteke za Android Studio (avtomatsko generirano)

**settings.gradle**
- Določa module, vključene v projekt


### Dodajanje nove funkcionalnosti
1. Ustvariš nov Empty views Activity: `App > kotlin+java > com.prvavaja.grocerease > Desni klik > New > Activity > Emty views Activity`
2. V datoteki `res > layout > activity_yourName` spreminjate in določite izgled aktivnosti 

---

## Baza podatkov

### Opis baze podatkov
Baza podatkov je zasnovana za shranjevanje uporabniških podatkov, kot so ime uporabnika (username), elektronski naslov (email), geslo (password) in slika profila (profile_image).
Vse te informacije so shranjene v zbirki podatkov MongoDB, ki je postavljena znotraj Docker okolja.
Uporabljamo Mongoose knjižnico za definicijo in interakcijo z MongoDB.

- **Platforma baze**: MongoDB Atlas
- **Uporabnik baze**: `user:IigChwsYtIpq8R21`
- **Ime baze**: `grocerease`
- **Cluster**: `Cluster0`

### Struktura podatkov v MongoDB

#### Kolekcija: Users
- **user_id**: Enolični identifikator uporabnika
- **name**: Ime uporabnika
- **email**: E-poštni naslov uporabnika
- **hashed_password**: Zgoščeno geslo uporabnika

#### Kolekcija: Lists
- **list_id**: Enolični identifikator nakupovalnega listka
- **user_id**: ID uporabnika, ki je ustvaril nakupovalni listek
- **items**: Polje, ki vsebuje ID-je izdelkov iz kolekcije `Items`

#### Kolekcija: Items
- **item_id**: Enolični identifikator izdelka
- **trgovina**: Ime trgovine, kjer je izdelek na voljo
- **ime**: Ime izdelka
- **st_izdelkov**: Število izdelkov na nakupovalnem listku
- **note**: Dodatne opombe k izdelku
---

## Zaledni del - API

### Opis zalednega dela
Zaledni del sistema (API) je zasnovan za upravljanje uporabniških podatkov, kot so registracija, prijava in pridobivanje uporabniških profilov.
API uporablja **Express.js** za obravnavo HTTP zahtev in **MongoDB** za shranjevanje uporabniških podatkov.
Za zaščito uporabniških podatkov in omogočanje varne prijave je implementirana **JWT (JSON Web Token)** avtentifikacija.

**Tehnologije in orodja:**
- **Node.js**: Za razvoj strežnika in API-ja.
- **Express.js**: Knjižnica za enostavno ustvarjanje RESTful API-jev.
- **MongoDB**: Dokumentna baza podatkov za shranjevanje uporabniških podatkov.
- **Mongoose**: Knjižnica za modeliranje in interakcijo z MongoDB.
- **JWT (JSON Web Tokens)**: Za varno avtentifikacijo uporabnikov in zaščito API končnih točk.

**Funkcionalnosti API-ja:**
- **Registracija uporabnika (`/register`)**: Uporabnik lahko registrira svoj račun z uporabniškim imenom, e-pošto, geslom in morebitno sliko profila. API preveri, če uporabnik že obstaja, in shrani novega uporabnika v bazo.

- **Prijava uporabnika (`/login`)**: Uporabnik se lahko prijavi z e-pošto in geslom. Če so podatki pravilni, API ustvari **JWT žeton**, ki ga uporabnik nato uporablja za dostop do zaščitenih virov.

- **Pridobivanje uporabniškega profila (`/profile`)**: Avtentificirani uporabniki lahko dostopajo do svojih podatkov (uporabniško ime, e-pošta, slika profila). API preveri veljavnost JWT žetona in vrne ustrezne podatke uporabnika.

**Povezava z bazo podatkov:**
API uporablja **Mongoose** za povezovanje in interakcijo z MongoDB bazo podatkov. Uporabniki so shranjeni v zbirki "User", kjer so podatki shranjeni v naslednji strukturi:
- **username**: Uporabniško ime uporabnika.
- **email**: Elektronski naslov uporabnika.
- **password**: Geslo uporabnika (shranjeno zakodirano).
- **profile_image**: Povezava do slike profila.

**Varnost in zaščita:**
- Gesla so shranjena zakodirana.
- **JWT** se uporablja za zaščito API končnih točk, kjer je uporabnikom omogočen dostop do zaščitenih virov samo, če so ustrezno avtentificirani.
- API uporablja **CORS (Cross-Origin Resource Sharing)** za dovoljenje povezave iz drugih domen, kar omogoča, da je aplikacija dostopna iz različnih virov, kot so frontend aplikacije.

**Povezava z MongoDB:**
- **MongoDB Atlas** je uporabljen za gostovanje baze podatkov.
- Povezava z bazo je vzpostavljena z uporabo **Mongoose** in URI povezave do MongoDB.

**Backend strežnik:**
Node.js api teče v Docker vsebniku, ki je postavljen v oblak.
- Oblak: Oracle cloud
- IP: **204.216.219.141**
- Operacijski sistem: Ubuntu Linux 22.10
- Vrata za api: 5000
- Api endpoint: https://204.216.219.141/api
---

## Še ne dokončane funkcionalnosti
- **Pridobivanje in pošiljanje podatkov preko baze**: Aplikacija lahko prejema in pošilja podatke na ustvarjeno in povezano MongoDB bazo
- **Dodajanje vnaprej določenih izdelkov (brez internetne povezave)**: Funkcija bi omogočala uporabniku, da
  lahko zemljevid uporablja tudi za dodajanje izdelkov. Ti izdelki bi bili shranjeni lokalno
  v json datotekah. Uporabnik bi lahko v nakupovalni list dodal izdelek tako, da
  na zemljevidu izbere trgovino in prikazali bi se vsi izdelki, ki so z njo povezani. Nato bi samo pritisnil
  na izdelek, ki ga želi dodati v seznam.
- **Dodajanje vnaprej določenih izdelkov (internetna povezava)**: Funkcija deluje enako kot `Dodajanje vnaprej določenih izdelkov (brez internetne povezave)`. Edina
  razlika je v tem da bi se izdelki hranili v bazi podatkov in ne lokalno.
- **Web sraping za izbrane trgovine**: Ločeni program, ki bi zbral podatke o izdelkih iz izbranih
  trgovin s spleta in jih nato hranil v bazo. Omogoča prikaz samo najnovejših izdelkov za uporabo pri funkciji: `Dodajanje vnaprej določenih izdelkov (internetna povezava)`.

---

## Skupina TechSavvy

**Filip Aljaž Stopar**, **Ervin Haračić**, **Matevž Sladič**, **Anja Ostovršnik**