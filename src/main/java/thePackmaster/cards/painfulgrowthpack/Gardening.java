package thePackmaster.cards.painfulgrowthpack;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.orbs.painfulgrowthpack.Seed;

public class Gardening extends AbstractPainfulGrowthCard {

    public static final String ID = SpireAnniversary5Mod.makeID(Gardening.class.getSimpleName());

    public Gardening(){
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.isInnate = false;
    }

    @Override
    public void upp() {
        this.isInnate = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChannelAction(new Seed()));
    }
}
