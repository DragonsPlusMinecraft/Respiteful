# Respiteful 1.4.0 Release Checklist

This document separates automated evidence from the client-only and migration checks that must be
performed before publishing. Do not publish from a working tree that has skipped a manual section.

## Automated blocking checks

Run with JDK 17:

```text
./gradlew clean build
./gradlew runData
./gradlew runData
git diff --exit-code
./gradlew runGameTestServer
```

`build.yml` uploads `respiteful-1.4.0-rc.1` as the GitHub-only test artifact and repeats the seven
GameTests on all supported loader points:

- Forge 47.1.33
- Forge 47.4.10
- Forge 47.4.22
- legacy NeoForge 1.20.1-47.1.106

It also runs the tests on Forge 47.4.22 with these isolated optional compatibility packs:

| `compatibility_pack` | Contents |
|---|---|
| `create_jei` (default) | Create 6.0.8, JEI 15.20.0.112, Gallery 1.0.3, AppleSkin 2.5.1 |
| `emi_tconstruct` | EMI 1.1.24, Mantle 1.11.104, Tinkers' Construct 3.11.2.166 |
| `thermal` | CoFH Core 11.0.2.56, Thermal Core 11.0.6.24, Thermal Foundation 11.0.6, JEI 15.20.0.112 |
| `bucketlib` | BucketLib 2.3.8.0 |

To reproduce one pack locally:

```text
./gradlew runGameTestServer -Pforge_version=47.4.22 -Pcompatibility_pack=thermal
```

## Client-only compatibility checks

GameTests prove that registry loading and the no-bucket invariant hold, but cannot open creative,
JEI, EMI, casting, or machine screens. Launch each profile below and record the log and result in
the release ticket.

```text
./gradlew runClient -Pforge_version=47.4.22 -Pcompatibility_pack=create_jei
./gradlew runClient -Pforge_version=47.4.22 -Pcompatibility_pack=emi_tconstruct
./gradlew runClient -Pforge_version=47.4.22 -Pcompatibility_pack=thermal
./gradlew runClient -Pforge_version=47.4.22 -Pcompatibility_pack=bucketlib
```

For every profile:

- Create a world and reopen it after one clean shutdown.
- Open the creative inventory and search every Respiteful item.
- Confirm that all four machine fluids appear as source fluids and that no `_bucket` item exists.
- Confirm the coffee seed, coffee bean, coffee shrub, and creative-mode scenarios from the original
  CurseForge reports do not crash. If they cannot be reproduced on 1.3.0 either, retain the exact
  mod list and request the reporter's crash log instead of assigning a speculative fix.

Profile-specific checks:

- `create_jei`: browse fluid and spout categories, inspect all four Create filling/pouring recipes,
  and move each fluid through a Create tank and pipe.
- `emi_tconstruct`: browse EMI fluid/casting categories and place each source fluid in a Tinkers'
  Construct tank or casting-compatible container. No registry lookup or missing-bucket exception is
  allowed.
- `thermal`: browse Thermal fluid/machine categories and insert each source fluid into a compatible
  Thermal tank or machine.
- `bucketlib`: open creative and recipe views while BucketLib scans fluid content. It must not
  synthesize or resolve a Respiteful bucket.
- In the default profile, verify AppleSkin renders all five ice creams and four milkshakes without a
  client exception.

Repeat a production-JAR client and dedicated-server startup on all four loader points. The Gradle
userdev result is necessary but does not replace this packaged-JAR smoke test.

## 1.3.0 world migration

1. Back up the test world.
2. With Respiteful 1.3.0, store every old item in a chest; place all three cakes and at least one
   candle variant of each cake.
3. Store all four machine fluids in Create tanks and save the world.
4. Replace only Respiteful with the 1.4.0 release candidate while retaining compatible upgraded
   prerequisites.
5. Open the world on Forge 47.1.33, then repeat from the backup on Forge 47.4.22 and legacy
   NeoForge 47.1.106.
6. Reject the release for any missing mapping, missing item/block, emptied tank, remapped registry
   ID, or level-load exception.

The automated `allPre140RegistryAndRecipeIdsRemainAvailable` GameTest guards the old registry and
recipe namespace, but it does not replace this serialized-world test.

## Release candidate and publication

Build the candidate without changing the stable version committed in `gradle.properties`:

```text
./gradlew clean build -Pmod_version=1.4.0-rc.1
```

Upload `1.4.0-rc.1` only as a GitHub test artifact. After all checks above pass, build the committed
`1.4.0` version and publish it.

After publication, query CurseForge and Modrinth and verify:

- game version is exactly Minecraft 1.20.1;
- Forge and NeoForge loaders are both present;
- Blueprint, Neapolitan, Farmer's Delight, and Farmer's Respite are four independent required
  dependencies;
- the uploaded JAR manifest reports `Implementation-Version: 1.4.0` and
  `Specification-Version: 1.4.0`.

Finally, attach the tested matrix and logs to issues #20, #23, #27, #29, #32, and #33 before closing
them. Any other 1.20.1 report must receive either a matching code/test reference or a documented
non-reproduction result.
