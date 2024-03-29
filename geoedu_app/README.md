# Opis projektu

Projekt `geoedu` to projekt aplikacji, służącej do nauki geografii. Aplikacja umożliwia rozwiązywanie testów, opartych na prostych zadaniach. Program pozwala na tworzenie własnych zadań i przypisywanie ich do danych testów.

# Lista funkcjonalności

Aplikacja posiada następujące funkcjonalności:
- Rozwiązywanie zadań zamkniętych ABCD z jedną poprawną odpowiedzią;
- Rozwiązywanie zadań otwartych, wymagających od użytkownika wpisania poprawnej odpowiedzi;
- Zadania mogą posiadać przypisane do nich zdjęcia, np. mapę, co pozwala na tworzenie ciekawszych zadań;
- Rozwiązywanie testów opartych na danej tematyce (rzeki, stolice, mapy)
- Poznanie wyniku punktowego po rozwiązaniu testu;
- Możliwość przejścia przez pytania testu po jego rozwiązaniu i zobaczenia, gdzie użytkownik popełnił błędy i jakie odpowiedzi są poprawne;
- Możliwość stworzenia nowego zadania i przypisania go do istniejącego testu. Można stworzyć zarówno zadanie otwarte, jak i zamknięte. Zadaniu przypisać można też istnięjący w plikach źródłowych programu obraz.

# Instrukcja uruchomienia

Głównym sposobem uruchomienia programu, jest podwójne kliknięcie na plik `geoedu.jar` znajdujący się w głównym katalogu programu. Żeby program działał, należy mieć zainstalowane `Java SE Runtime Environment 8` lub `JDK Development Kit 17`. Oprogramowanie dostępne jest [tutaj](https://www.oracle.com/pl/java/technologies/downloads/#java17).

Jeżeli program nie włącza się po podwójnym kliknięciu, uruchomić go można również poprzez terminal (np. cmd) poleceniem:

`java -jar geoedu.jar`

(Żeby polecenie zadziałało, należy znajdować się w głównym katalogu `Zes2`.)



# Opis repozytorium

## Opis katalogów

- Główny katalog repozytorium zawiera plik opisowy README.md, plik projektu pom.xml oraz plik wykonywalny geoedu.jar;
- `Data` zawiera dane wykorzystywane przez program - zdjęcia używane w zadaniach (katalog `photos`) i pliki tekstowe z pytaniami (katalog `tests`);
- `Git_potwierdzenie` zawiera zrzuty ekranu potwierdzające profesjonalne wyszkolenie członków zespołu w sprawach systemu kontroli wersji Git;
- `src` zawiera kod źródłowy programu. Jest on podzielony na:
    - `main`, gdzie znajduje się cały kod źródłowy programu końcowego;
    - `test`, gdzie znajduje się kod odpowiedzialny za testy jednostkowe oprogramowania;

## Opis pakietów programu

### core

Pakiet `core` zawiera główne klasy programu związane z jego działaniem:
- klasa abstrakcyjna `Question` odpowiedzialna jest za pytanie. Korzystają z niej dwie klasy pokrewne: 
  - `OpenEndedQuestion` odpowiedzialne za pytanie otwarte;
  - `SingleChoiceQuestion` odpowiedzialne za pytanie zamknięte ABCD;
- klasa `Test` odpowiedzialna za test.

### ui

Pakiet `ui` zawiera klasy obsługujące interfejs graficzny programu:
- klasa `Theme` zawiera stałe wartości używane w interfejsie programu, takie jak kolory i czcionki;
- klasa `Gui` jest główną klasą odpowiedzialną z interfejs graficzny;
- klasa `TestSelectionPanel` jest odpowiedzialna za ekran wyboru testu, który chcemy rozwiązać;
- klasa `TestPanel` jest odpowiedzialna za interfejs testu: ułożenie treści zadania, zdjęcia, odpowiedzi oraz poruszanie się po teście;
- klasa `ResultPanel` jest odpowiedzialna za wyświetlenie wyniku testu oraz za poruszanie się po teście i podgląd poprawnych odpowiedzi po jego rozwiązaniu;
- klasa `AddPanel` jest odpowiedzialna za interfejs dodawania nowych zadań;
- funkcjonalności w klasach `ResourcePanel` i `ViewPanel` nie zostały zaimplementowane. Są to puste panele, do których można dostać się z menu bocznego.

## Użyte technologie

Projekt oparty jest na narzędziu Maven. Umożliwia łatwiejsze zarządzanie zależnościami.

Wykorzystane w programie biblioteki:

- com.formdev FlatLaf 3.1
- org.apidesign.bck2brwsr emul 0.6
- junit 4.11
- assertj-core 3.24.2



# Autorzy
Bartosz Dybowski, Grzegorz Sawicki, Michał Janaszewski, Michał Ciechan