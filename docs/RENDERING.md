# Rendering System

This document describes how BasicRotor renders animated structures.

The rendering system is designed to be completely independent from gameplay logic and can be reused by future assembly types.

---

# Overview

BasicRotor renders animations using Minecraft's Block Entity rendering pipeline.

Instead of moving real blocks inside the world, the renderer draws virtual representations of blocks while the original blocks remain unchanged.

This approach keeps rendering lightweight, multiplayer-friendly, and independent from world updates.

---

# Rendering Pipeline

The current rendering pipeline is:

```text
RotorBlockEntity
        │
        ▼
extractRenderState()
        │
        ▼
RotorBlockEntityRenderState
        │
        ▼
submit()
        │
        ▼
PoseStack Transform
        │
        ▼
BlockModelRenderState.submit()
        │
        ▼
Minecraft Renderer
```

Every rendered frame follows this pipeline.

---

# Current Renderer

The current renderer is responsible for:

* Reading block facing
* Reading animation rotation
* Updating model state
* Applying transforms
* Submitting the block model

It is **not** responsible for:

* Gameplay logic
* Animation calculation
* Redstone logic
* Assembly management

---

# Render State

The renderer transfers data through `RotorBlockEntityRenderState`.

Current data includes:

| Field       | Description                    |
| ----------- | ------------------------------ |
| modelState  | Block model used for rendering |
| facing      | Current block orientation      |
| rotation    | Current animation angle        |
| lightCoords | Combined block and sky light   |

The render state acts as a bridge between the BlockEntity and the renderer.

---

# Transform Order

Transforms are applied in the following order:

```text
Translate to block center
        │
        ▼
Apply spin rotation
        │
        ▼
Apply facing rotation
        │
        ▼
Translate back
```

The order is important.

Changing the order changes the rotation axis.

---

# Lighting

Lighting information is collected during `extractRenderState()`.

Current implementation uses:

* LightCoordsUtil
* BlockModelResolver

This ensures that rendered models receive the same lighting as normal Minecraft blocks.

---

# Multiplayer

Rendering is entirely client-side.

The server does not synchronize animation frames.

Instead, each client renders animations locally using gameplay state received from the server.

This keeps bandwidth usage low while maintaining consistent gameplay.

---

# Future Rendering Pipeline

The current renderer only renders a single rotor block.

Future versions will render complete assemblies.

```text
LinkedAssembly
        │
        ▼
AssemblyRenderer
        │
        ▼
for each LinkedBlock
        │
        ▼
Apply Transform
        │
        ▼
Submit Model
```

The renderer should not distinguish between windmills, turbines, gears, or any other machine.

It only renders assemblies.

---

# Design Rules

* Rendering must remain independent from gameplay.
* Rendering must never modify world state.
* Renderers consume transform data only.
* Transform calculation belongs to the movement system.
* Lighting should always match vanilla block rendering whenever possible.
