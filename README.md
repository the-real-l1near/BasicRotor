# BasicRotor

BasicRotor is a small Fabric mod that adds a redstone-controlled rotor capable of rotating linked blocks.

The goal of the mod is simple: provide a lightweight rotating block system without turning into a full mechanical automation mod.

## Features

- Redstone-controlled rotor
- Smooth acceleration and braking
- Rotor can stop at its current angle
- Right-click an idle rotor with an empty hand to return it to its home position
- Link blocks to a rotor using the Assembly Wrench
- Linked blocks rotate together with the rotor
- Assemblies persist across world restarts
- Multiple rotors can operate independently
- Works across different dimensions
- Custom rendering for moving blocks
- Per-block lighting for rotating assemblies
- Block breaking overlay for the rotor

## Requirements

- Minecraft 26.2
- Fabric Loader 0.19.3 or newer
- Fabric API

## Usage

### Rotor

Place a Rotor and power it with redstone.

When powered, the rotor accelerates and begins rotating.

When redstone power is removed, the rotor slows down and stops at its current angle.

While the rotor is idle, right-click it with an empty hand to return it to its original position.

### Assembly Wrench

The Assembly Wrench is used to create or edit a rotor assembly.

1. Right-click a Rotor to select it.
2. Right-click blocks to add them to the assembly.
3. Right-click the selected Rotor again to finish.

For blocks with their own interaction, use Duck + Right-click to link them instead of activating them.

Right-clicking a different Rotor changes the current selection to that Rotor.

The Rotor must be stopped and unpowered while editing its assembly.

Blocks already linked to another Rotor cannot be linked again.

Some blocks are intentionally not linkable.

## Crafting

Both the Rotor and Assembly Wrench have crafting recipes and are available through the recipe book.

## Notes

While an assembly is moving, its linked blocks are temporarily represented as virtual blocks by the client renderer.

When the rotor returns to its home position, the blocks are restored to the world.

BasicRotor currently focuses on simple rotating assemblies. It is not intended to provide gears, shafts, power networks, or a full mechanical simulation.

## Development

BasicRotor currently uses a server-authoritative design:

- The server owns movement and assembly state.
- The client handles interpolation and rendering.
- Assembly data is persisted per world.
- Moving linked blocks are rendered virtually while their original world blocks are temporarily removed.

More technical details are available in the [`docs`](docs/) directory.

## License

# BasicRotor

BasicRotor is a small Fabric mod that adds a redstone-controlled rotor capable of rotating linked blocks.

The goal of the mod is simple: provide a lightweight rotating block system without turning into a full mechanical automation mod.

## Features

- Redstone-controlled rotor
- Smooth acceleration and braking
- Rotor can stop at its current angle
- Right-click an idle rotor with an empty hand to return it to its home position
- Link blocks to a rotor using the Assembly Wrench
- Linked blocks rotate together with the rotor
- Assemblies persist across world restarts
- Multiple rotors can operate independently
- Works across different dimensions
- Custom rendering for moving blocks
- Per-block lighting for rotating assemblies
- Block breaking overlay for the rotor

## Requirements

- Minecraft 26.2
- Fabric Loader 0.19.3 or newer
- Fabric API

## Usage

### Rotor

Place a Rotor and power it with redstone.

When powered, the rotor accelerates and begins rotating.

When redstone power is removed, the rotor slows down and stops at its current angle.

While the rotor is idle, right-click it with an empty hand to return it to its original position.

### Assembly Wrench

The Assembly Wrench is used to create or edit a rotor assembly.

1. Right-click a Rotor to select it.
2. Right-click blocks to add them to the assembly.
3. Shift + Right-click the selected Rotor to finish.

The Rotor must be stopped and unpowered while editing its assembly.

Blocks already linked to another Rotor cannot be linked again.

Some blocks are intentionally not linkable.

## Crafting

Both the Rotor and Assembly Wrench have crafting recipes and are available through the recipe book.

## Notes

While an assembly is moving, its linked blocks are temporarily represented as virtual blocks by the client renderer.

When the rotor returns to its home position, the blocks are restored to the world.

BasicRotor currently focuses on simple rotating assemblies. It is not intended to provide gears, shafts, power networks, or a full mechanical simulation.

## Development

BasicRotor currently uses a server-authoritative design:

- The server owns movement and assembly state.
- The client handles interpolation and rendering.
- Assembly data is persisted per world.
- Moving linked blocks are rendered virtually while their original world blocks are temporarily removed.

More technical details are available in the [`docs`](docs/) directory.

## License

BasicRotor is licensed under the MIT License.