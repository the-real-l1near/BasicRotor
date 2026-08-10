# Changelog

All notable changes to BasicRotor will be documented in this file.

## [Unreleased]

### Added

- No unreleased changes yet.

## [1.0.0] - 2026-08-10

### Added

- Redstone-controlled Rotor
- Smooth acceleration, braking, and automatic return
- Six-direction Rotor placement
- Assembly Wrench for linking blocks
- Editable linked assemblies
- Multiple independent Rotor assemblies
- Per-world assembly persistence
- Dimension-aware assembly and client state
- Virtual rendering for moving linked blocks
- Per-block lighting for virtual assemblies
- Rotor breaking overlay support
- Crafting recipes for the Rotor and Assembly Wrench
- Recipe book unlocks
- First-Rotor advancement
- English and Vietnamese localization
- Creative mode entries

### Changed

- Linked block states are refreshed before virtualization
- Linked assemblies are restored to a stopped physical state after world load
- Rotor mining behavior and drops were aligned with normal Survival gameplay

### Fixed

- Rotor lighting on different mounting directions
- Rotor facing and rotation orientation
- Client rendering state conflicts between dimensions
- Assembly persistence and runtime reattachment
- Linked block lighting while rotating
- Rotor breaking texture rendering with the custom renderer
- Several assembly lifecycle and validation edge cases

### Known Limitations

- Full Block Entity data preservation is not complete
- Blocks with complex internal data or special rendering may not be fully supported