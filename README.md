# APK 
* Path app/release

# Funktionsbeschreibung
Die Todo-App ermöglicht es Nutzern, Aufgaben (Todos) zu erstellen, zu bearbeiten, abzuschließen und zu löschen. Die App bietet zwei Hauptansichten:

* Aktive Todos: Hier werden alle Aufgaben angezeigt, die noch nicht abgeschlossen sind.
* Abgeschlossene Todos: Diese Ansicht zeigt alle Todos, die als erledigt markiert wurden.

Wichtige Funktionen:
* Erstellen von Todos: Nutzer können neue Aufgaben mit Namen, Beschreibung, Priorität une einem Fälligkeitsdatum hinzufügen.
* Bearbeiten von Todos: Bestehende Aufgaben können bearbeitet werden, um Informationen wie Name, Beschreibung, Priorität und Deadline zu aktualisieren.
* Löschen von Todos: Nutzer können Todos dauerhaft entfernen.
* Fälligkeitsdatum und Priorität: Beim Erstellen oder Bearbeiten eines Todos kann ein Fälligkeitsdatum gesetzt werden, und es kann eine Priorität (hoch, mittel, niedrig) zugewiesen werden.
* Todos als erledigtmarkieren: Nutzer können Todos als abgeschlossen kennzeichnen, wodurch sie in der entsprechenden Ansicht angezeigt werden.

# Verwendete Technologien
* Kotlin: Kotlin ist eine moderne Programmiersprache, die von Google als bevorzugte Sprache für die Android-Entwicklung empfohlen wird. Sie bietet eine kompaktere Syntax und nullsichere Operationen, was die Entwicklung effizienter und sicherer macht.

* Jetpack Compose: Eine moderne UI-Toolkit-Bibliothek von Google, die eine deklarative UI-Entwicklung ermöglicht. Mit Jetpack Compose wird das Layout und das UI-Design der App erstellt, wodurch eine einfachere und flexiblere Benutzeroberfläche erzielt wird.

* Material Design 3: Die App nutzt Material Design 3 für ein modernes und benutzerfreundliches UI. Komponenten wie Buttons, Textfelder, Card-Layouts und mehr werden durch Material Design bereitgestellt.

* SQLite: Eine leichtgewichtige relationale Datenbank, die für die lokale Speicherung von Daten auf dem Gerät verwendet wird. In dieser App werden die Todos in einer SQLite-Datenbank gespeichert, die beim ersten Start aus den Assets der App kopiert wird.

# Bekannte Probleme
* Fehlende Validierung von Eingaben:
  Die App überprüft derzeit nur, ob der Name des Todos nicht leer ist, wenn ein neues Todo erstellt oder bearbeitet wird. Weitere Eingabevalidierungen (z.B. für die Beschreibung oder die Priorität) sind nicht implementiert.

* Fehlende Tests:
  Die App enthält derzeit keine automatisierten Tests (Unit-Tests oder UI-Tests). Dies könnte dazu führen, dass unerwartete Fehler auftreten, wenn Änderungen an der App vorgenommen werden.

* Kein Dark Mode Support:
Die App unterstützt derzeit keinen Dark Mode. Benutzer können nur den Standard-Modus verwenden
