package plus.dragons.respiteful;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReleaseMetadataTest {
    private static final Path PROJECT = Path.of(System.getProperty("respiteful.projectDir"));
    private static final List<String> FLUIDS = List.of(
        "mint_green_tea", "vanilla_milk_tea", "adzuki_milk_tea", "mocha_coffee");
    private static final List<String> MILKSHAKES = List.of(
        "green_tea_milkshake", "yellow_tea_milkshake", "black_tea_milkshake", "coffee_milkshake");

    @Test
    void releaseAndDependencyBaselineIsPinned() throws IOException {
        String properties = read("gradle.properties");
        assertContains(properties,
            "minecraft_version_range=[1.20.1,1.20.2)",
            "forge_version=47.1.33",
            "forge_version_range=[47.1.33,48)",
            "loader_version_range=[47,48)",
            "mod_version=1.4.0",
            "blueprint_version = 7.1.4",
            "blueprint_version_spec = [7.1.3,8)",
            "neapolitan_version = 5.1.0",
            "neapolitan_version_spec = [5.1.0,6)",
            "farmersdelight_version = 1.20.1-1.3.2",
            "farmersdelight_version_spec = [1.20.1-1.3.2,1.20.1-1.4)",
            "farmersrespite_version = 1.20.1-2.1.2",
            "farmersrespite_version_spec = [1.20.1-2.1,1.20.1-2.2)",
            "jei_version = 15.20.0.112",
            "create_version = 6.0.8",
            "emi_version = 1.1.24+1.20.1+forge",
            "tconstruct_version = 1.20.1-3.11.2.166",
            "thermal_core_version = 1.20.1-11.0.6.24",
            "thermal_foundation_version = 1.20.1-11.0.6.70",
            "bucketlib_version = 1.20.1-2.3.8.0");

        String modsToml = read("src/main/resources/META-INF/mods.toml");
        for (String modId : List.of("blueprint", "neapolitan", "farmersdelight", "farmersrespite")) {
            assertTrue(modsToml.contains("modId=\"" + modId + "\""), "missing required mod " + modId);
        }
    }

    @Test
    void registryIdsStayStableAndMachineFluidsHaveNoBuckets() throws IOException {
        String registrations = read("src/main/java/plus/dragons/respiteful/entries/RespitefulFluids.java");
        for (String fluid : FLUIDS) {
            assertTrue(registrations.contains("tea(\"" + fluid + "\""), "missing source/type id " + fluid);
            assertFalse(Files.exists(PROJECT.resolve(
                "src/generated/resources/assets/respiteful/models/item/" + fluid + "_bucket.json")));
        }
        assertTrue(registrations.contains("FLUIDS.register(\"flowing_\" + name"));
        assertFalse(registrations.contains(".noBucket()"));
    }

    @Test
    void milkshakeRecipesProduceThreeBottles() throws IOException {
        for (String milkshake : MILKSHAKES) {
            String recipe = read("src/generated/resources/data/respiteful/recipes/" + milkshake + ".json");
            assertContains(recipe,
                "\"count\": 3",
                "\"tag\": \"forge:milk\"",
                "\"item\": \"respiteful:" + milkshake + "\"");
            assertEquals(3, occurrences(recipe, "\"item\": \"minecraft:glass_bottle\""), milkshake);
        }
    }

    @Test
    void generatedCauldronAndCuttingDataIsComplete() throws IOException {
        String cauldrons = read("src/generated/resources/data/minecraft/tags/blocks/cauldrons.json");
        for (String milkshake : MILKSHAKES) {
            assertTrue(cauldrons.contains("respiteful:" + milkshake + "_cauldron"));
            assertTrue(Files.exists(PROJECT.resolve(
                "src/generated/resources/assets/respiteful/blockstates/" + milkshake + "_cauldron.json")));
            assertFalse(Files.exists(PROJECT.resolve(
                "src/generated/resources/assets/respiteful/models/item/" + milkshake + "_cauldron.json")));
            String loot = read("src/generated/resources/data/respiteful/loot_tables/blocks/"
                + milkshake + "_cauldron.json");
            assertTrue(loot.contains("\"name\": \"minecraft:cauldron\""), milkshake);
        }
        for (String tea : List.of("green_tea", "yellow_tea", "black_tea")) {
            String cutting = read("src/generated/resources/data/respiteful/recipes/cutting/" + tea + "_cake.json");
            assertContains(cutting, "\"tag\": \"farmersdelight:tools/knives\"", "\"count\": 7");
        }
    }

    @Test
    void bothPublishingPlatformsDeclareAllRequiredProjects() throws IOException {
        String build = read("build.gradle");
        assertContains(build,
            "id 'me.modmuss50.mod-publish-plugin' version '2.1.1'",
            "client = true",
            "server = true",
            "environment = CLIENT_AND_SERVER");
        assertContains(read("gradle/wrapper/gradle-wrapper.properties"),
            "gradle-9.0.0-bin.zip");
        Map<String, String> dependencies = Map.of(
            "blueprint", "VsM5EDoI",
            "neapolitan", "InYMuiQt",
            "farmers-delight", "R2OftAxM",
            "farmers-respite", "fasdaXH5");
        for (Map.Entry<String, String> dependency : dependencies.entrySet()) {
            assertEquals(1, occurrences(build,
                "[curseforge: \"" + dependency.getKey() + "\","), dependency.getKey());
            assertEquals(1, occurrences(build,
                "modrinth: \"" + dependency.getValue() + "\"]"), dependency.getValue());
        }
        assertEquals(2, occurrences(build, "requiredModProjects.each"));
        assertTrue(build.contains("requires { slug = dependency.curseforge }"));
        assertTrue(build.contains("requires { slug = dependency.modrinth }"));
        assertFalse(build.contains("vNoRmTC3"));
    }

    @Test
    void changelogContainsOnlyTheCurrentRelease() throws IOException {
        String changelog = read("CHANGELOG.md");
        assertEquals(1, occurrences(changelog, "## Respiteful "));
        assertTrue(changelog.startsWith("## Respiteful 1.4.0"));
    }

    @Test
    void automationUsesJava17() throws IOException {
        for (String workflow : List.of("build.yml", "publish.yml", "publish-to-maven.yml", "publish-to-mod-platform.yml")) {
            String yaml = read(".github/workflows/" + workflow);
            assertTrue(yaml.contains("java-version: '17'"), workflow);
            assertFalse(yaml.contains("java-version: '21'"), workflow);
        }
        String build = read(".github/workflows/build.yml");
        assertContains(build,
            "Forge 47.1.33",
            "Forge 47.4.10",
            "Forge 47.4.22",
            "NeoForge 47.1.106",
            "-Plegacy_neoforge_version=1.20.1-47.1.106",
            "clean build -Pmod_version=1.4.0-rc.1",
            "name: respiteful-1.4.0-rc.1",
            "id: emi_tconstruct",
            "id: thermal",
            "id: bucketlib",
            "-Pcompatibility_pack=${{ matrix.pack.id }}");
    }

    private static String read(String relative) throws IOException {
        return Files.readString(PROJECT.resolve(relative));
    }

    private static void assertContains(String text, String... fragments) {
        for (String fragment : fragments) {
            assertTrue(text.contains(fragment), "missing: " + fragment);
        }
    }

    private static int occurrences(String text, String needle) {
        int count = 0;
        int offset = 0;
        while ((offset = text.indexOf(needle, offset)) >= 0) {
            count++;
            offset += needle.length();
        }
        return count;
    }
}
