package UI.components.components;

import BLL.entity.npc.actions.NPCAction;

public interface IDialog extends IComponent {
    // Events

    void onAnswer(IEventListener<Boolean> listener);

    void onContinue(IEventListener listener);

    void onQuizAnswer(IEventListener<Integer> listener);

    // Methods
    void loadCharacterInformation(String characterName, String message, String imagePath);

    void addChoices(NPCAction action);

}
