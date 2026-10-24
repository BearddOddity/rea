# Can we replace Google's OS on the Streamer 4K?

Short answer: **not in any supported or practical way**. Please treat the details below as my
understanding, to be verified on your unit, not as tested facts.

- The Google TV Streamer uses a locked bootloader with verified boot, and Google has not
  published an unlock path for it. Custom firmware needs a bootloader or ROM-level exploit.
- A reverse-engineering route means dumping firmware, finding a bootloader/TEE/kernel vulnerability, and
  building a custom image for an undocumented SoC. That is a long research project with a real brick risk,
  and it would likely break Widevine L1 (so Netflix and others fall back to low quality or stop).
- Alternative hardware (Raspberry Pi, mini PC, or an unlockable Android box) with a custom Linux or
  AOSP image is the realistic way to a "true" OS, but you lose the Streamer's certified apps and DRM.

## Chosen path
1. **Now:** Home launcher app on the stock OS (this repo). Safe, reversible, keeps all streaming apps.
2. **Later, optional:** Same UI as an Android TV *ROM-free* shell on other hardware (AOSP or a Linux
   kiosk) if you want deeper control.
3. **Only if you want to research it:** Streamer bootloader analysis, as a separate, explicitly risky track.

## Launcher limits to know about
- Google TV may nudge users back to its own home; setting a default Home app can need `adb` or a helper.
- Some Google TV builds restrict third-party Home apps. This needs testing on the actual device.
