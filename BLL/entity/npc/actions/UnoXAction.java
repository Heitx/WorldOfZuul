/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL.entity.npc.actions;

import BLL.ACQ.INPCAction;
import BLL.ACQ.PersistenceLayer;
import BLL.entity.npc.NPC;
import BLL.entity.npc.UnoX;
import BLL.entity.player.Player;

/**
 *
 * @author lalal
 */
public class UnoXAction implements NPCActionCollection {
    private INPCAction[] actions;
    UnoX unox;
    
    public UnoXAction() {
        actions = new INPCAction[] {
            new NPCAction("Before leaving you make a quick stop at the local gas station Uno-X"),
            new NPCDialogAction("Would you like to play a small quiz in order to win some gas for your ship?") {
                @Override
                public void onEndEvent(Player player, NPC npc, PersistenceLayer persistenceLayer) {
                    super.onEndEvent(player, npc, persistenceLayer);

                    if(answerYes) {
                        unox.pickRandomQuiz();
                    }
                }
            },
            new NPCAction("... Till we meet again!")
        };
    }

    @Override
    public INPCAction[] getActions() {
        return actions;
    }
    
}
