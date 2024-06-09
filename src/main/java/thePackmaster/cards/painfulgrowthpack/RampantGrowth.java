package thePackmaster.cards.painfulgrowthpack;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.powers.painfulgrowthpack.GrowingPainPower;

import static thePackmaster.util.Wiz.atb;

public class RampantGrowth extends AbstractPainfulGrowthCard {

    public static final String ID = SpireAnniversary5Mod.makeID(RampantGrowth.class.getSimpleName());

    private static final int BLOCK = 6;
    private static final int BLOCK_UPG = 2;

    private static final int GP = 10;

    public RampantGrowth(){
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = BLOCK;
        this.baseMagicNumber = GP;

        this.block = this.baseBlock;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void upp() {
        upgradeBlock(BLOCK_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainBlockAction(p, p,this.block));
        atb(new GainBlockAction(p, p,this.block));
        atb(new ApplyPowerAction(p, p, new GrowingPainPower(p, this.magicNumber), this.magicNumber));
    }
}
