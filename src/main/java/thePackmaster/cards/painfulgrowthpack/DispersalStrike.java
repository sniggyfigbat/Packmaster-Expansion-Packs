package thePackmaster.cards.painfulgrowthpack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.orbs.painfulgrowthpack.Seed;
import thePackmaster.powers.painfulgrowthpack.GrowingPainPower;

import static thePackmaster.util.Wiz.atb;

public class DispersalStrike extends AbstractPainfulGrowthCard {

    public static final String ID = SpireAnniversary5Mod.makeID(DispersalStrike.class.getSimpleName());

    private static final int ATTACK = 14;
    private static final int ATTACK_UPG = 6;

    private static final int TEMP_DEX = 4;
    private static final int TEMP_DEX_UPG = 1;

    private static final int GP = 4;
    private static final int GP_UPG = 1;

    public DispersalStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = ATTACK;
        this.baseMagicNumber = GP;
        this.baseSecondMagic = TEMP_DEX;

        this.damage = this.baseDamage;
        this.magicNumber = this.baseMagicNumber;
        this.secondMagic = this.baseSecondMagic;
    }

    @Override
    public void upp() {
        upgradeDamage(ATTACK_UPG);
        upgradeMagicNumber(GP_UPG);
        upgradeSecondMagic(TEMP_DEX_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        atb(new ApplyPowerAction(p, p, new DexterityPower(p, this.secondMagic), this.secondMagic));
        atb(new ApplyPowerAction(p, p, new LoseDexterityPower(p, this.secondMagic), this.secondMagic));
        atb(new ApplyPowerAction(p, p, new GrowingPainPower(p, this.magicNumber), this.magicNumber));
        atb(new ChannelAction(new Seed()));
    }
}
