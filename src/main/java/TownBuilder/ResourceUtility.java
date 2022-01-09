package TownBuilder;

import TownBuilder.DebugApps.DebugTools;
import TownBuilder.UI.BoardUI;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ResourceUtility {




    private ArrayList<ResourceEnum> resourceArray = new ArrayList<>(Arrays.asList(ResourceEnum.WOOD, ResourceEnum.BRICK, ResourceEnum.WHEAT, ResourceEnum.GLASS, ResourceEnum.STONE));
    private Board board;
    public Object getNotifier() {
        return notifier;
    }

    private final Object notifier;

    public ResourceUtility(Board b) {
        notifier = new Object();
        board = b;
    }

    public ResourceEnum resourcePicker(ArrayList<ResourceEnum> blacklistedResources, BoardUI boardUI) throws IOException {
        resetResourceArray();
        DebugTools.logging("Opening Resource Picker.");
        ResourceEnum resourceChoice = ResourceEnum.GLASS;
        boolean validResource = true;
        boardUI.promptResourceSelection(true);
        System.out.println("Prompted selection panel");
        //if (blacklistedResources != null) {
            do {
                System.out.println("Opening notifier");
                synchronized (notifier) {
                    try {
                        notifier.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Passed notifier");
                resourceChoice = boardUI.getUserSelectedResource();
                if (blacklistedResources != null) {
                    for (ResourceEnum resource : blacklistedResources) {
                        validResource = (resource == resourceChoice);
                    }
                }
                if (!validResource) {
                    boardUI.setSecondaryTextLabel("That resource is unavailable to choose. Please pick another.", Color.RED);
                }
            }

            while (!validResource);
            System.out.println("Escaped loop");
            boardUI.promptResourceSelection(false);
        //}
        return resourceChoice;
    }

    public ResourceEnum randomResource() {
        ResourceEnum result;
        try {
            int random = (int) (Math.random() * resourceArray.size());
            result = resourceArray.get(random);
            resourceArray.remove(random);
        }
        catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            result = ResourceEnum.GLASS;
        }
        return result;
    }

    public void resetResourceArray() {
       resourceArray = new ArrayList<>(Arrays.asList(ResourceEnum.WOOD, ResourceEnum.BRICK, ResourceEnum.WHEAT, ResourceEnum.GLASS, ResourceEnum.STONE));
    }
}
