---
name: testing-java-console-app
description: How to build, run and end-to-end test the plain-Java console app in this repo (Mini Library Management System) — compile commands, scripted stdin testing, and a recorded interactive terminal run.
---

# Testing the plain-Java console app (TRRY)

No build tool, no dependencies, default package. JDK 17 is installed system-wide (`javac`, `java` on PATH).

## Build & run

```bash
cd /path/to/TRRY
javac -Xlint:all -d out src/*.java     # -Xlint:all catches warnings; expect none
java -cp out LibraryManagement
```
The README also documents `cd src && javac Book.java LibraryManagement.java && java LibraryManagement`
— that works too, but leaves `.class` files in `src/`; delete them afterwards (`rm -f src/*.class`)
so `git status` stays clean. `out/` is untracked and should not be committed.

## Fast adversarial coverage: scripted stdin

The menu reads one line per prompt, so a whole session can be driven by `printf`. This is the quickest
way to cover error branches; capture the transcript as evidence.

```bash
printf '1\n101\nClean Code\nRobert Martin\nProgramming\n5\n101\n8\n9\n' \
  | java -cp out LibraryManagement
echo "exit=$?"          # 9 must exit 0
```
Tips:
- Grep the log for `Error|Success|Total` to get a compact assertion view.
- Non-ASCII input works: `java -Dfile.encoding=UTF-8 -cp out LibraryManagement` with UTF-8 bytes in `printf`.
- **Always end the script with `9`.** If stdin closes while a prompt is pending, `Scanner.nextLine()`
  throws `java.util.NoSuchElementException: No line found` and the JVM exits 1. That is a real
  (unguarded-EOF) bug class in this code — also reproducible with Ctrl+D in an interactive run — so if
  you see that trace, check whether it's the app's missing `hasNextLine()` guard rather than your script.

## Recorded interactive run (for user-visible evidence)

Only `konsole` is installed on this box (no xterm/gnome-terminal). Launch it readable and maximized:

```bash
konsole --hide-menubar --hide-tabbar -p Font="Monospace,14" \
        -p TerminalColumns=120 -p TerminalRows=40 --workdir /path/to/TRRY &
sleep 5; wmctrl -a Konsole; wmctrl -r :ACTIVE: -b add,maximized_vert,maximized_horz
```
- Do NOT use `ctrl+shift+plus` to grow the font — Konsole ignores it and the literal `+++` gets typed
  into the shell. Set the font via the `-p Font=` profile property instead.
- The table output is 90 columns wide, so keep the terminal ≥100 columns.
- Type input with the computer-use `type` action, one prompt per action, with ~1s waits. Never send
  `clear` while the Java app is in the foreground — it is consumed as a menu answer and produces
  `Error: Please enter a valid whole number.` Use option `2`'s own output or exit first.
- CJK/emoji characters cannot be typed reliably through xdotool; verify unicode via piped stdin and say
  so in the report rather than faking it on camera.
- Show the exit code on screen with `java -cp out LibraryManagement; echo "EXIT CODE = $?"`. Note that
  `${PIPESTATUS[0]}` after a pipe into `tail` reports the wrong status — redirect to a file instead.

## Known cosmetic behaviour to expect

`Book.displayBook()` uses `%-25s/%-20s/%-15s`, which pads but never truncates, so values longer than a
column push the `|` separators past the `+---+` border. Wide CJK glyphs cause similar drift. Report as
cosmetic, not a crash.

## Devin Secrets Needed

None — the app is fully local with no network, credentials or services.
