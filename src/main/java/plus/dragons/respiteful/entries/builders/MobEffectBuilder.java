package plus.dragons.respiteful.entries.builders;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.AbstractBuilder;
import com.tterrag.registrate.builders.Builder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonnullType;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.common.util.NonNullFunction;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class MobEffectBuilder<T extends MobEffect, P> extends AbstractBuilder<MobEffect, T, P, MobEffectBuilder<T, P>> {
    @SuppressWarnings("deprecation")
    public static final ProviderType<RegistrateTagsProvider<MobEffect>> MOB_EFFECT_TAGS = ProviderType
        .register("tags/mob_effect", type -> (p, e) -> new RegistrateTagsProvider<>(
            p, type, "mob_effect", e.getGenerator(), Registry.MOB_EFFECT, e.getExistingFileHelper()));
    private final NonNullBiFunction<MobEffectCategory, Integer, T> factory;
    protected MobEffectCategory category = MobEffectCategory.NEUTRAL;
    protected int color = 0xFFFFFF;
    @Nullable
    protected String description;
    
    public static <T extends MobEffect, P> MobEffectBuilder<T, P> create(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, NonNullBiFunction<MobEffectCategory, Integer, T> factory) {
        return new MobEffectBuilder<>(owner, parent, name, callback, factory).defaultLang();
    }
    
    protected MobEffectBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, NonNullBiFunction<MobEffectCategory, Integer, T> factory) {
        super(owner, parent, name, callback, ForgeRegistries.Keys.MOB_EFFECTS);
        this.factory = factory;
    }
    
    /**
     * Assign the {@link MobEffectCategory} for this effect.
     * This should be called in most cases, as the default category is {@link MobEffectCategory#NEUTRAL}.
     * @param category The {@link MobEffectCategory} to assign
     * @return this {@link MobEffectBuilder}
     */
    public MobEffectBuilder<T, P> category(MobEffectCategory category) {
        this.category = category;
        return this;
    }
    
    /**
     * Assign the color for this effect.
     * This should be called in most cases as the default color is #FFFFFF(white).
     * @param color The color to assign
     * @return this {@link MobEffectBuilder}
     */
    public MobEffectBuilder<T, P> color(int color) {
        this.color = color & 0xFFFFFF;
        return this;
    }
    
    public MobEffectBuilder<T, P> description(String description) {
        this.description = description;
        return this;
    }
    
    @SafeVarargs
    public final MobEffectBuilder<T, P> tag(TagKey<MobEffect>... tags) {
        return this.tag(MOB_EFFECT_TAGS, tags);
    }
    
    /**
     * Assign the default translation, as specified by {@link RegistrateLangProvider#getAutomaticName(NonNullSupplier)}.
     * This is the default, so it is generally not necessary to call, unless for undoing previous changes.
     *
     * @return this {@link MobEffectBuilder}
     */
    public MobEffectBuilder<T, P> defaultLang() {
        return lang(MobEffect::getDescriptionId);
    }
    
    /**
     * Set the translation for this effect.
     *
     * @param name
     *            A localized English name
     * @return this {@link MobEffectBuilder}
     */
    public MobEffectBuilder<T, P> lang(String name) {
        return lang(MobEffect::getDescriptionId, name);
    }
    
    /**
     * Set the lang key for this entry to the default value (specified by {@link RegistrateLangProvider#getAutomaticName(NonNullSupplier)}). Generally, specific helpers from concrete builders should be used
     * instead.
     *
     * @param langKeyProvider
     *            A function to get the translation key from the entry
     * @return this {@link Builder}
     */
    @Override
    public MobEffectBuilder<T, P> lang(NonNullFunction<T, String> langKeyProvider) {
        return lang(langKeyProvider, RegistrateLangProvider::getAutomaticName);
    }
    
    /**
     * Set the lang key for this entry to the specified name. Generally, specific helpers from concrete builders should be used instead.
     *
     * @param langKeyProvider
     *            A function to get the translation key from the entry
     * @param name
     *            The name to use
     * @return this {@link Builder}
     */
    @Override
    public MobEffectBuilder<T, P> lang(NonNullFunction<T, String> langKeyProvider, String name) {
        return lang(langKeyProvider, (p, s) -> name);
    }
    
    private MobEffectBuilder<T, P> lang(NonNullFunction<T, String> langKeyProvider, NonNullBiFunction<RegistrateLangProvider, NonNullSupplier<? extends T>, String> localizedNameProvider) {
        return setData(ProviderType.LANG, (ctx, prov) -> {
            String langKey = langKeyProvider.apply(ctx.getEntry());
            prov.add(langKey, localizedNameProvider.apply(prov, ctx::getEntry));
            if (description != null) {
                prov.add(langKey + ".description", description);
            }
        });
    }
    
    @Override
    protected @NonnullType T createEntry() {
        return factory.apply(category, color);
    }
    
}
