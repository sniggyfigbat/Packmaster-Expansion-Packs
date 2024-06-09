package thePackmaster.cards.painfulgrowthpack;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.powers.painfulgrowthpack.GrowingPainPower;

import static thePackmaster.util.Wiz.adp;

public class NewGrowth extends AbstractPainfulGrowthCard {

    public static final String ID = SpireAnniversary5Mod.makeID(NewGrowth.class.getSimpleName());

    private static final int BLOCK_PER_GP = 1;
    private static final int BLOCK_PER_GP_UPG = 1;

    public NewGrowth() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseMagicNumber = BLOCK_PER_GP;
        this.magicNumber = this.baseMagicNumber;

        this.isEthereal = true;
        this.exhaust = true;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(BLOCK_PER_GP_UPG);
    }

    @Override
    public void applyPowers() {
        AbstractPower gp = adp().getPower(GrowingPainPower.POWER_ID);
        this.baseBlock = gp != null ? gp.amount * this.magicNumber : 0;
        super.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.applyPowers();
        this.addToBot(new GainBlockAction(p, this.block));
    }
}
