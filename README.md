# Docufy

**Avvertenze:**
Questo progetto è stato realizzato a scopo didattico.

## Copyright
Tutte le icone e immagini presenti nel sito sono fornite da:
- [Freepik](https://www.freepik.com/)
- [BootstrapIcon](https://icons.getbootstrap.com/)

## Descrizione
Docufy è un progetto sviluppato nell'ambito dell'esame di Web Application, con l'obiettivo di gestire documenti aziendali. Il focus principale è sulla creazione di un sistema di scadenziario che notifichi all'azienda documenti in procinto di scadere o già scaduti. Inoltre, la piattaforma permette la gestione dell'anagrafica aziendale e la partecipazione ai corsi offerti da un consulente.

La piattaforma è suddivisa in due rami principali:
- **Ramo Consulente:** Si occupa della gestione delle aziende che richiedono consulenza. Il consulente può visualizzare le informazioni delle aziende partner e offrire corsi personalizzati.
- **Ramo Azienda:** Si occupa del caricamento e dell'aggiornamento della documentazione necessaria. Gestisce anche i dati dei dipendenti e la loro documentazione associata.
A seconda della tipologia di utente loggato (azienda/consulente) si presentano delle variazioni nell'interfaccia grafica e cambiano le operazioni che è possibile effettuare sulla piattaforma.

## Funzionalità Comuni
- **Login:** Supporta account multipli con possibilità di rimanere collegati per una maggiore comodità.
- **Ricerca:** Ricerca Globale di aziende, documenti e dipendenti eseguibile comodamente dall'header in ogni momento.
- **Grafici Documenti In Scadenza:** Consente di avere una panoramica generale dei documenti che si trovano negli stati "In Scadenza" e/o "Scaduti".
- **Tabella Documenti In Scadenza:** Utile per offrire una visualizzazione alternativa al grafico delle scadenza imminenti.
- **Documenti**: Consente una visualizzazione agevolate dei propri documenti (sia aziendali che del singolo dipendente) sottoforma tabellare, con la possibilità di ordinare i dati per specifici attributi.
- **Download Documenti**: Consente di scaricare i documenti (di vari formati) direttamente nel browser.
- **Dipendenti**: Consente la visualizzazione dei propri dipendenti in forma tabellare.
- **Visione completa delle anagrafiche:** Aggiunge informazioni sui dipendenti rispetto alla sola forma tabellare.
- **Corsi**: Visualizzazione dei corsi tramite card indicanti le loro caratteristiche principali e la disponibilità di posti.
- **Visualizzazione e Modifica Profilo:** Consente di visualizzare e modificare i dati aziendali, inclusa la possibilità di cambiare o eliminare l'immagine e di effettuare il cambio password.
- **F.A.Q.**: Consente di cercare delle risposte alle domande che vengono poste più di frequente riguardo la piattaforma.
- **Contatti**: Consente di inviare una richiesta specifica al servizio di assistenza qualora l'utente non avesse trovato le risposte che cercava nella pagina delle F.A.Q. All'invio della richiesta di assistenza, viene inviata una copia della richiesta sull'e-mail del richiedente.

## Funzionalità riservate al Consulente:
- **Creazione di nuovi account Aziendali:** Attraverso questa funzione si vuole sostituire la classica fase di registrazione per le aziende, con un processo maggiormente automatizzato che possa legare l'azienda al proprio consulente al momento della creazione. Dopo aver creato un profilo aziendale, il sistema invierà un'e-mail all'indirizzo specificato contenti le credenziali di accesso alla piattaforma.
- **Eliminazione di un’azienda:** Comporta anche l’eliminazione dell’account associato.
- **Documenti suddivisi per azienda:** Visualizzazione dei documenti aziendali di ogni azienda partner.
- **Dipendenti suddivisi per azienda:** Visualizzazione dei dipendenti aziendali di ogni azienda partner, con relativa documentazione.
- **Gestione corsi:** Possibilità di aggiungere, modificare o eliminare corsi offerti alle aziende partner. (I corsi si intendono come personalizzati per ogni azienda, quindi, due aziende diverse non possono accedere allo stesso corso).

## Funzionalità riservate all’Azienda:
- **Credenziali:** Le credenziali dell’account vengono inviate all’azienda via e-mail al momento della creazione dell’account.
- **Gestione Documenti:** Consente l'aggiunta o la modifica dei documenti aziendali e quelli riguardanti i dipendenti.
- **Gestione Dipendenti:** Consente l'aggiunta o la rimozione di nuovi dipendenti, o di una lista di dipendenti. (La lista di dipendenti viene caricato attraverso degli appositi file in formato csv o xlsx, cui template può essere scaricato direttamente dalla piattaforma nell'apposita sezione "Aggiungi Lista di Dipendenti")
- **Partecipazione ai corsi:** Permette l'aggiunta di dipendenti ai corsi aziendali. (N.B I corsi prevedono una capienza massima di iscritti)
- **Notifiche:** Ricezione di solleciti via e-mail nel caso di documenti aziendali in scadenza.

##Nota sull'aggiunta e modifica dei dati
Tutti i form riguardanti l'aggiunta e la modifica di dati del database (aziende, documenti, dipendenti, corsi) vengono gestiti attraverso delle finestre modali o pop-up. I pop-up in questione gestiscono dati diversi tra di loro: testi, date, immagini, file, etc. 
Durante l'operazioni di modifica dei dati, i pop-up vengono ripristinati con le informazioni di quella specifica entità, riducendo notevolmente i tempi per effettuare l'operazione.

## Tecnologie utilizzate:

### Lato Client:
- Implementazione della parte frontend con framework Angular e Bootstrap.
- Realizzazione di file HTML, CSS e JS personalizzati.
- Sito interamente responsive tramite l'utilizzo del framework bootstrap e delle apposite direttive del CSS '@media'

## Lato Server:
- Implementazione del modello MVC (Model-View-Controller)
- Servlet classiche: raggiungibili attraverso collegamenti ipertestuali dal server di backend. In particolare, la servlet realizzata e quella riguardante la visualizzazione tabellare dei dipendenti della propria azienda, con la possibilità di rimuovere i dipendenti (se si dispongono dei permessi. Ad esempio, il consulente non può eliminare dei dipendenti). I dati vengono inseriti nel template HTML attraverso Thymeleaf.
- Adozione del pattern DAO per l’accesso al database Postgres.
- Adozione del pattern Proxy per gestire i differenti permessi sui dati. In particolare, si è scelto di adottare un Proxy di Sicurezza per separare al meglio le funzionalità tra il consulente e l'azienda. Inoltre, grazie all'utilizzo del pattern, diventa molto più semplice aggiungere in futuro altre tipologie di utenti. Ad esempio, si potrebbe pensare di garantire l'accesso alla piattaforma al singolo dipendente. Per fare ciò sarebbe sufficiente creare un nuovo utente concreto definendo i metodi e sue operazioni.
- Relazioni del modello dei dati in Java gestite come oggetti e non chiavi esterne.
- Implementazione dei @RestController e utilizzo dei JSON per il trasferimento di file

## Librerie esterne ed API
- **ChartJS:** Grafico riguardante scadenza imminenti di documenti presenti nella dashboard.
- **JavaMail:** Per l'integrazione del servizio e-mail (invio credenziali di accesso, contattare assistenza, notifiche documenti in scadenza).
- **Web3Forms:** Per gestire le richieste e-mail di assistenza.

## Credenziali di Test
Nel dump del database sono presenti 3 utenti di prova (2 aziende e 1 consulente).

### Azienda Alpha
- Email: alpha@azienda.com
- Password: Web2324.

### Azienda Beta
- Email: beta@azienda.com
- Password: Web2324.

### Azienda Gamma Consulenze
- Email: gamma@consulente.com
- Password: Web2324.

## Autori:
- Campanella Gianluca
- Curcio Daniel
- Falvo Caterina
- Gattuso Pietro
