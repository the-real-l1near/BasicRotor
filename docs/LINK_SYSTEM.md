# Link System

This document describes how Rotor assemblies are created, edited, validated, and restored.

## Overview

A Rotor can own a group of linked blocks.

Linked blocks are selected with the Assembly Wrench and stored as a `LinkedAssembly`.

Each linked block is stored relative to the Rotor origin.

## Linking Workflow

The normal workflow is:

1. Right-click a Rotor with the Assembly Wrench.
2. Right-click blocks to add them.
3. Right-click the selected Rotor again to finish.

For blocks with their own interaction, Duck + Right-click can be used to link the block instead of activating it.

Right-clicking a different Rotor changes the current selection to that Rotor.

The Rotor must be:

- Stopped
- Unpowered

while its assembly is being edited.

## Link Sessions

Temporary selections are stored in a per-player `LinkSession`.

A session contains:

- The selected Rotor position
- The selected block positions

Nothing is written to the final assembly until the player finishes the linking process.

This prevents incomplete selections from modifying the saved assembly.

## Editing Existing Assemblies

Selecting a Rotor that already has an assembly preloads its linked blocks into the current `LinkSession`.

This allows additional blocks to be added without rebuilding the entire assembly from scratch.

Linked blocks are not removed by clicking them again.

To remove a linked block, break the block normally.

## Ownership

A block can belong to only one committed assembly.

Before a block is added, the server checks whether it is already owned by another Rotor.

If it is already linked, the selection is rejected.

## Validation

Blocks are validated before they are added to an assembly.

The final selection is also validated again when the player finishes the assembly.

This second validation prevents invalid blocks from being inserted through world changes that happen after the initial selection.

Some blocks are intentionally not linkable.

Examples include:

- Other Rotors
- Gravity-affected blocks such as sand and gravel
- System or technical blocks that should not be moved

The blacklist is intentionally conservative and can be adjusted as compatibility issues are discovered.

## Assembly Creation

When the player finishes linking:

1. The selected block positions are converted to positions relative to the Rotor.
2. Their current block states are stored.
3. A `LinkedAssembly` is created.
4. The Rotor receives the assembly.
5. The server's `AssemblyManager` registers it.
6. Persistent assembly data is updated.
7. Clients receive the updated assembly snapshot.

## Virtualization

Linked blocks remain normal world blocks while the Rotor is stopped.

When movement begins:

1. Their current block states are refreshed.
2. The physical blocks are removed from the world.
3. The assembly becomes virtualized.
4. The client renders the linked blocks as part of the rotating assembly.

The stored assembly data remains on the server.

## Restoration

When the Rotor returns to its home position:

1. Virtual rendering stops.
2. Stored blocks are placed back into the world.
3. The assembly remains registered to the Rotor.

The blocks can then move again the next time the Rotor starts.

## Breaking Linked Blocks

Breaking a linked block removes it from the assembly.

The updated assembly is saved and synchronized to clients.

Breaking the Rotor removes the assembly itself.

If the assembly is virtualized when the Rotor is removed, its stored blocks are restored first.

## Persistence

Committed assemblies are saved per world.

After a server restart, saved assemblies are reattached to their Rotors.

Assemblies load in a stopped physical state rather than resuming movement from a partially virtualized state.

## Current Limitations

The link system currently stores block states, but full Block Entity data support is limited.

Blocks with important internal data, inventories, or other complex state may require additional handling in future versions.

There is currently no hard assembly size or radius limit.