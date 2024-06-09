package thePackmaster.orbs.painfulgrowthpack;

import basemod.abstracts.CustomOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import com.megacrit.cardcrawl.vfx.scene.TorchParticleXLEffect;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.powers.painfulgrowthpack.GrowingPainPower;
import thePackmaster.powers.shamanpack.IgnitePower;
import thePackmaster.util.Wiz;
import thePackmaster.vfx.spherespack.BlazeOrbActivateEffect;
import thePackmaster.vfx.spherespack.BlazeOrbFlareEffect;

import java.util.Objects;

import static thePackmaster.SpireAnniversary5Mod.makePath;
import static thePackmaster.util.Wiz.adp;
import static thePackmaster.util.Wiz.atb;

public class Seed extends CustomOrb {
    public static final String ORB_ID = SpireAnniversary5Mod.makeID(Seed.class.getSimpleName());
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String NAME = orbString.NAME;
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    private static final String IMG_PATH = makePath("/images/orbs/spherespack/Blaze.png");
    private static final float SPIRIT_WIDTH = 96.0f;

    private final static int BASE_PASSIVE = 1;

    private float vfxTimer = 0.2f;

    private final BobEffect bobEffect = new BobEffect(2f, 3f);

    public Seed() {
        // passiveDescription and evokeDescription aren't provided as we override updateDescription() anyway.
        super(ORB_ID, NAME, BASE_PASSIVE, BASE_PASSIVE, "", "", IMG_PATH);
        checkCurrentPowersAndHandle();
    }

    public static void onPowersModified() {
        if (!Wiz.isInCombat()) { return; }

        for (AbstractOrb orb : adp().orbs) {
            if (Objects.equals(orb.ID, ORB_ID)) {
                ((Seed)orb).checkCurrentPowersAndHandle();
            }
        }
    }

    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("SOTE_SFX_FireIgnite_2_v1.ogg", 0.1f);
    }

    @Override
    public void applyFocus() {
        checkCurrentPowersAndHandle();
    }

    @Override
    public void onEndOfTurn() {
        this.checkCurrentPowersAndHandle();

        float speedTime = Settings.FAST_MODE ? 0.0F : 0.6F / (float)AbstractDungeon.player.orbs.size();
        atb(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), speedTime));
        atb(new ApplyPowerAction(
                adp(),
                adp(),
                new GrowingPainPower(adp(), this.passiveAmount), this.passiveAmount
        ));
        atb(new DamageRandomEnemyAction(
                new DamageInfo(adp(), this.passiveAmount, DamageInfo.DamageType.THORNS),
                AbstractGameAction.AttackEffect.SLASH_VERTICAL
        ));
    }

    @Override
    public void onEvoke() {
        checkCurrentPowersAndHandle();

        float speedTime = Settings.FAST_MODE ? 0.0F : 0.6F / (float)AbstractDungeon.player.orbs.size();
        atb(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), speedTime));

        AbstractPower gp = Wiz.isInCombat() ? adp().getPower(GrowingPainPower.POWER_ID) : null;
        if (gp != null) {
            atb(new RemoveSpecificPowerAction(
                    adp(),
                    adp(),
                    gp
            ));
            if (gp.amount > 0) {
                atb(new DamageAllEnemiesAction(
                        adp(),
                        DamageInfo.createDamageMatrix(gp.amount, true),
                        DamageInfo.DamageType.THORNS,
                        AbstractGameAction.AttackEffect.SLASH_VERTICAL
                ));
            }
        }
    }

    @Override
    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("SOTE_SFX_FireIgnite_2_v1.ogg", 0.1f);
        AbstractDungeon.effectsQueue.add(new BlazeOrbActivateEffect(this.cX, this.cY));
    }

    @Override
    public void updateAnimation() {
        bobEffect.update();

        vfxTimer -= Gdx.graphics.getDeltaTime();

        cX = MathHelper.orbLerpSnap(cX, adp().animX + tX);
        cY = MathHelper.orbLerpSnap(cY, adp().animY + tY);
        if (channelAnimTimer != 0.0F) {
            channelAnimTimer -= Gdx.graphics.getDeltaTime();
            if (channelAnimTimer < 0.0F) {
                channelAnimTimer = 0.0F;
            }
        }

        c.a = Interpolation.pow2In.apply(1.0F, 0.01F, channelAnimTimer / 0.5F);
        scale = Interpolation.swingIn.apply(Settings.scale, 0.01F, channelAnimTimer / 0.5F);

        if (vfxTimer <= 0) {
            AbstractDungeon.effectsQueue.add(
                    new TorchParticleXLEffect(cX + MathUtils.random(-30.0F, 30.0F) * Settings.scale,
                            cY + MathUtils.random(-25.0F, 25.0F) * Settings.scale));
            vfxTimer = MathUtils.random(0.05f, 0.5f);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE.cpy());
        sb.setBlendFunction(770, 771);
        sb.draw(img, cX - SPIRIT_WIDTH /2F, cY - SPIRIT_WIDTH /2F + bobEffect.y, SPIRIT_WIDTH /2F, SPIRIT_WIDTH /2F,
                SPIRIT_WIDTH, SPIRIT_WIDTH, scale, scale, 0f, 0, 0, (int) SPIRIT_WIDTH, (int) SPIRIT_WIDTH,
                false, false);
        this.renderText(sb);
        this.hb.render(sb);
    }

    @Override
    protected void renderText(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 4.0F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET + 20.0F * Settings.scale, this.c, this.fontScale);
    }

    @Override
    public void updateDescription() {
        checkCurrentPowersAndHandle();
    }

    @Override
    public AbstractOrb makeCopy() {
        return new Seed();
    }

    private void checkCurrentPowersAndHandle()
    {
        AbstractPower power = adp().getPower(FocusPower.POWER_ID);
        this.passiveAmount = power != null ? Math.max(0, this.basePassiveAmount + power.amount) : this.basePassiveAmount;

        AbstractPower gp = Wiz.isInCombat() ? adp().getPower(GrowingPainPower.POWER_ID) : null;
        this.evokeAmount = (gp != null) ? gp.amount : 0;

        if (this.evokeAmount > 0) {
            this.description = DESCRIPTIONS[1].replace("{0}", this.passiveAmount + "").replace("{1}", this.evokeAmount + "");
        }
        else {
            this.description = DESCRIPTIONS[0].replace("{0}", this.passiveAmount + "");
        }
    }
}