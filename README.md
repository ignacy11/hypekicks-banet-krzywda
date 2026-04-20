# 👟 HypeKicks - System Zarządzania Sklepem Streetwearowym

Witajcie w projekcie **HypeKicks**! Waszym zadaniem jest stworzenie kompleksowej aplikacji mobilnej w systemie Android (Kotlin).
Aplikacja ma pełnić dwie funkcje: 
1. **Dla klientów:** Piękny, wizualny katalog z limitowanymi butami i wyszukiwarką.
2. **Dla szefa:** Ukryty panel administracyjny do dodawania i usuwania butów z magazynu.

---

## 🧠 Cykl Życia Oprogramowania (SDLC) - Zanim napiszesz linijkę kodu!

W profesjonalnych firmach IT nikt nie otwiera edytora kodu w pierwszej minucie projektu! 
Pracujemy zgodnie z **SDLC (Software Development Life Cycle)**. 
Rzucenie się od razu do pisania kodu to gwarancja chaosu i frustracji. 

Wasza praca powinna przejść przez 5 faz:
1. **📝 Analiza Wymagań:** Przeczytajcie dokładnie ten plik. Zrozumcie, co dokładnie musicie dostarczyć (3 ekrany, jakie dane, jakie technologie).
2. **📐 Projektowanie (Design):** Weźcie kartkę papieru. Narysujcie makiety 3 ekranów. Zaplanujcie bazę danych w Firestore. Rozpiszcie zadania na tablicy **Kanban**.
3. **💻 Implementacja (Kodowanie):** Mając plan, odpalacie Android Studio. Dzielicie się zadaniami, tworzycie gałęzie (branches) i programujecie.
4. **🐛 Testowanie:** Uruchamiacie aplikację. Czy dodanie buta w Panelu Szefa od razu pokazuje go na Widoku Klienta? Czy usuwanie działa?
5. **🚀 Wdrożenie (Deployment):** Złączenie działającego kodu do gałęzi `main` (przez Pull Request) i zgłoszenie gotowego projektu!

Punkty 4 i 5 możemy pominąć.

---

## 🛠️ Technologie i Narzędzia
* **Język:** Kotlin
* **Interfejs:** XML + ViewBinding
* **Baza danych:** Firebase Firestore (chmura NoSQL)
* **Pobieranie zdjęć:** Biblioteka Glide
* **Hosting zdjęć:** [Postimages.org](https://postimages.org/) (darmowy CDN dla Waszych grafik - kopiujcie "Link bezpośredni")

---

## 🎯 Wymagania Projektowe (Kryteria Akceptacji MVP)
Aby projekt został zaliczony, aplikacja musi posiadać **3 Aktywności (Ekrany)**:

**1. Baza Firestore:** Utwórzcie kolekcję `sneakers`. Każdy dokument to para butów posiadająca pola:
* `brand` (String), `modelName` (String), `releaseYear` (Number), `resellPrice` (Number), `imageUrl` (String - link z Postimages).

**2. Widok Klienta (`StorefrontActivity` - GridView):** * Wyświetla siatkę aut (`GridView`). Na kafelku widać okładkę (Glide), markę i model.
* Na górze `SearchView` do filtrowania butów po nazwie modelu "w locie".
* Posiada przycisk/ikonę przenoszącą do Panelu Administratora.

**3. Panel Administratora (`AdminPanelActivity` - ListView + CRUD):**
* Prosta lista tekstowa (`ListView`) wszystkich butów z bazy.
* **CREATE:** Nad listą znajduje się formularz (pola `EditText` na dane i link) oraz przycisk "DODAJ DO BAZY". Zapisuje nowego buta w Firestore.
* **DELETE:** Przytrzymanie palca (`setOnItemLongClickListener`) na elemencie listy usuwa buta z bazy.

**4. Szczegóły Buta (`DetailsActivity`):**
* Ekran otwierany po kliknięciu w buta na Widoku Klienta (`GridView`).
* Odbiera dane przez `Intent` (pamiętajcie o `Serializable`).
* Pokazuje wielkie zdjęcie, ładnie sformatowaną cenę i posiada przycisk powrotu.
* **Czysty powrót:** Przy powrocie na listę główną wyszukiwarka ma być wyczyszczona (`onResume()`).

---

## ⚠️ Workflow i Praca w Zespole (Git & GitHub)
* **Gałąź `main` jest święta!** Znajduje się tam tylko przetestowany kod.
* Nowe funkcje piszecie na osobnych gałęziach (np. `feature/admin-panel`, `feature/grid-view`).
* Nie kasujecie gałęzi.
* Łączenie kodu odbywa się **WYŁĄCZNIE** przez Pull Requesty zatwierdzane przez partnera.
* Pilnujcie tablicy **Kanban**!

---

## 🚀 Zaczynamy krok po kroku

1. **Faza Planowania:** Załóżcie repozytorium z szablonu. Zróbcie szkice ekranów. Zalogujcie się do Firebase, utwórzcie nowy projekt i podepnijcie aplikację. 
   **🚨 UWAGA:** Pobierzcie plik `google-services.json` i wklejcie go do folderu `app` w Android Studio! Bez tego baza nie ruszy.
2. **Faza Implementacji:** Podzielcie się rolami. Ktoś robi logikę bazy i CRUD w Panelu Szefa, ktoś inny projektuje piękną siatkę dla Klienta.
3. **Finał:** Połączcie kod, przetestujcie pełen obieg (dodanie buta -> znalezienie go w wyszukiwarce -> wejście w detale) i zgłoście projekt!

Powodzenia! Planujcie mądrze, kodujcie czysto i niech kompilator będzie z Wami! 💻✨
