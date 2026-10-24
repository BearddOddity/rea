# DaybreakOS

A SteamOS-style TV home experience for the **Google TV Streamer (4K)**, with swappable dashboards
(Deck, Xbox 360, PS5, Switch 2), native gamepad support, and the original remote, while staying a
full smart TV (Netflix, YouTube, Plex and the rest keep working).

**Status: v0.1 scaffold. Written but not yet built or run on a device.**

## Approach
Replacing the Streamer's OS is not realistic (see [docs/FEASIBILITY.md](docs/FEASIBILITY.md)). DaybreakOS is
instead an Android TV **Home launcher** that the Streamer treats as its home screen, so the
"OS feel" is ours and everything underneath (apps, DRM, Cast, updates) is stock.

## What's here
- `theme/` — dashboard themes and layouts (`SIDEBAR_GRID` for Deck, `TILE_ROW` for the console themes).
- `input/InputMapper` — one action model for remote D-pad, gamepad (A/B/Y/Start/Select) and keyboard.
- `apps/AppRepository` — lists installed TV apps and launches them.
- `ui/Dashboard` — Compose UI with focus rings for D-pad/controller navigation.

## Controls
D-pad / left stick move, A or OK selects, **Y / Select / Alt cycles dashboards**, Menu/Start reserved.
A "Dashboards" list is also on screen, so the remote alone can switch.

## Build
Needs Android Studio or JDK 17 + Android SDK 35, then `gradle :app:assembleDebug :app:testDebugUnitTest`.
Install with `adb connect <streamer-ip>` then `adb install app/build/outputs/apk/debug/app-debug.apk`.
Pick it as default home via Settings, or with a home-replacement helper app.

## Roadmap
1. Build and verify on the Streamer; add a real banner and app icon.
2. Xbox 360 blade layout, real fonts/art, sounds, focus animations.
3. Game library (emulator and Steam Link/Moonlight shortcuts), controller-first quick menu.
4. Settings, Wi-Fi/HDMI-CEC shortcuts, per-profile themes.
