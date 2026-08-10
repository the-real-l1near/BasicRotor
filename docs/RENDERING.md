# Rendering

This document describes how BasicRotor renders Rotors and moving linked blocks.

## Overview

BasicRotor uses a custom `BlockEntityRenderer` for the Rotor.

The Rotor block itself uses an invisible world render shape, while its model is submitted manually by the renderer.

The same renderer is also responsible for drawing linked blocks while an assembly is virtualized.

## Rotor Rendering

The Rotor renderer handles:

- Block facing
- Rotor spin
- Client-side interpolation
- Lighting
- Breaking overlay

The Rotor model is rendered around the block center.

Its final transform depends on both:

- The block's facing direction
- The current rotation angle

## Client Interpolation

The server owns the actual movement state.

The client receives movement updates and interpolates between them to keep rotation smooth.

Client-side movement data stores:

- Current rotation
- Previous render rotation
- Rotation step
- Moving state

Interpolation only affects visuals.

It does not change server movement or assembly state.

## Facing

The Rotor can face all six block directions.

The renderer applies a facing transform so the same model can be reused for:

- North
- South
- East
- West
- Up
- Down

Spin rotation is then applied around the Rotor's local axis.

## Virtual Assemblies

When an assembly is moving, its physical linked blocks are temporarily removed from the world.

The client receives the assembly data and renders those blocks virtually.

Each virtual block keeps:

- Its relative position from the Rotor
- Its stored `BlockState`
- Its render model state
- Its sampled light value

The entire assembly is rotated around the Rotor origin.

## Virtual Block Position

Linked block positions are stored relative to the Rotor.

During rendering, their world-space visual position is calculated from:

- Rotor position
- Relative block position
- Rotor facing
- Current rotation

The real world block is not moved every frame.

Only its rendered representation moves.

## Lighting

Virtual blocks sample lighting independently.

The renderer first calculates the rotated world position of each linked block, then samples the light level at that position.

This allows a moving assembly to react more naturally when rotating between brighter and darker areas.

Lighting remains approximate because the virtual block is not actually present in the world at its rendered position.

## Block Models

Virtual linked blocks use their stored `BlockState` to resolve a normal Minecraft block model.

This allows many ordinary blocks to render without requiring custom models inside BasicRotor.

Block Entity rendering is not fully supported yet.

## Breaking Overlay

Because the Rotor uses a custom renderer, Minecraft's normal block breaking overlay is not automatically rendered on it.

BasicRotor handles the breaking overlay manually.

The renderer:

1. Reads the current breaking progress from the block entity render state.
2. Emits the Rotor block model into a Fabric render mesh.
3. Submits that mesh through the breaking block render path.

This keeps the normal Minecraft crack animation visible while mining the Rotor.

## Dimension-Aware Client State

Client assembly and movement data are keyed by:

- Dimension
- Rotor position

This prevents Rotors at the same coordinates in different dimensions from sharing client render state.

## Scope

The renderer is designed around simple rotating block assemblies.

It does not attempt to simulate real moving world geometry, collision, or complex physics.