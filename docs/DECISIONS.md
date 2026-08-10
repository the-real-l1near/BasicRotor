# Technical Decisions

This document records the main technical decisions currently used by BasicRotor.

The goal is to explain why the project is structured the way it is without turning this file into a full design history.

## Server Authority

Gameplay state is owned by the server.

The server controls:

- Rotor movement state
- Redstone input
- Assembly membership
- Virtualization
- Block removal and restoration
- Persistent assembly data

The client only handles presentation and interpolation.

This avoids client-side state becoming authoritative and keeps multiplayer behavior consistent.

## Movement State Machine

Rotor movement uses a small explicit state machine:

```text
STOPPED
   ↓
STARTING
   ↓
RUNNING
   ↓
BRAKING
   ↓
RETURNING
   ↓
STOPPED