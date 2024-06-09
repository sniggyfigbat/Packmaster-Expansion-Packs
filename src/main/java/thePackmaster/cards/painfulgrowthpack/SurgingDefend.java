package thePackmaster.cards.painfulgrowthpack;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.powers.painfulgrowthpack.GrowingPainPower;
import thePackmaster.util.Wiz;

import static thePackmaster.util.Wiz.atb;

public class SurgingDefend extends AbstractPainfulGrowthCard {

    public static final String ID = SpireAnniversary5Mod.makeID(SurgingDefend.class.getSimpleName());

    private static final int BLOCK = 5;
    private static final int BLOCK_UPG = 3;

    private static final int TEMP_STR = 3;
    private static final int TEMP_STR_UPG = 1;

    private static final int GP = 2;
    private static final int GP_UPG = 1;

    public SurgingDefend(){
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = BLOCK;
        this.baseMagicNumber = GP;
        this.baseSecondMagic = TEMP_STR;

        this.block = this.baseBlock;
        this.magicNumber = this.baseMagicNumber;
        this.secondMagic = this.baseSecondMagic;
    }

    @Override
    public void upp() {
        upgradeBlock(BLOCK_UPG);
        upgradeMagicNumber(GP_UPG);
        upgradeSecondMagic(TEMP_STR_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainBlockAction(p, p,this.block));
        atb(new ApplyPowerAction(p, p, new StrengthPower(p, this.secondMagic), this.secondMagic));
        atb(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.secondMagic), this.secondMagic));
        atb(new ApplyPowerAction(p, p, new GrowingPainPower(p, this.magicNumber), this.magicNumber));
    }
}
