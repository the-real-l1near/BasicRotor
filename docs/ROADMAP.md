# Roadmap

This roadmap tracks a small set of practical improvements for BasicRotor.

It is intentionally limited in scope.

## Current Priorities

### Block Entity Data Support

Improve support for linked blocks that store additional data.

Examples include:

- Chests
- Furnaces
- Hoppers
- Signs
- Banners
- Skulls

The main requirement is to preserve their data correctly when an assembly is virtualized, restored, saved, and loaded.

### Compatibility

Test more vanilla and modded blocks and adjust validation where needed.

Compatibility changes should be based on actual behavior rather than broad assumptions.

### Multiplayer Reliability

Continue testing:

- Multiple Rotors running at the same time
- Players joining while assemblies are active
- Dimension changes
- Reconnects
- Assembly editing with multiple players nearby

### Rendering Cleanup

Keep improving rendering only where visible issues exist.

Possible areas include:

- Special block models
- Block Entity rendering
- Lighting edge cases
- Breaking overlay compatibility

## Release Maintenance

Before each release:

- Run a clean build
- Test the release JAR in a clean Fabric instance
- Verify recipes and recipe book unlocks
- Verify advancements
- Verify localization
- Verify Rotor placement and mining
- Verify linking and restoration
- Verify world restart behavior
- Verify multiplayer behavior

## Out of Scope

BasicRotor does not currently plan to add:

- Mechanical power networks
- Gears
- Shafts
- Torque simulation
- Stress systems
- Complex physics
- Large automation systems

These may only be reconsidered if the scope of the mod changes significantly.