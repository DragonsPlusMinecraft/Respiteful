## Respiteful 1.4.0

### Added

- Added Green Tea, Yellow Tea, Black Tea, and Coffee Milkshakes using Neapolitan's `MilkshakeItem` behavior.
- Added matching three-level milkshake cauldrons with Neapolitan's milk-cauldron conversion, bottling, and level interactions.
- Added in-world knife slicing for normal and candle tea cakes.
- Added Chinese (Simplified and Traditional) names for the new items, blocks, and preserved fluid translation keys.
- Added release metadata and generated-data consistency checks.
- Added loader and optional integration CI matrices plus a reproducible manual release checklist.

### Changed

- Updated the 1.20.1 dependency baseline to Blueprint 7.1.4, Neapolitan 5.1.0, Farmer's Delight 1.20.1-1.3.2, Farmer's Respite 1.20.1-2.1.2, and JEI 15.20.0.112.
- Changed all five ice creams to Neapolitan's `IceCreamItem`: they now add frozen ticks and no longer apply Slowness.
- Standardized ice-cream nutrition and saturation while preserving Respiteful's flavor effects.
- Limited loader metadata to Minecraft `[1.20.1,1.20.2)` and Forge/legacy NeoForge loader 47.x.
- Made Create, Gallery, JEI, and AppleSkin optional development/runtime integrations.
- Made JAR metadata reproducible and set implementation/specification versions to 1.4.0.

### Fixed

- Replaced Registrate fluid builders with Forge native registrations for all four machine-only fluids.
- Preserved all source, flowing, fluid-type, item, block, recipe, effect, and configuration IDs.
- Removed all implicit `_bucket` lookups and the obsolete Create JEI spout mixin workaround.
- Updated Farmer's Delight cutting recipes from the removed `build(...)` API to `save(...)` without changing recipe IDs.
- Made Create pouring recipes explicitly reference source fluids.
- Fixed CurseForge and Modrinth publication metadata so all four required dependencies are emitted independently.

### Upgrade notes

- This release supports Minecraft 1.20.1 only.
- The same JAR supports Forge 47.x and legacy NeoForge 47.1.x.
- Back up worlds before upgrading from 1.3.x.
- The four kettle machine fluids intentionally do not have buckets.
