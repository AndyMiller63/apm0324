import java.math.BigDecimal;
import java.util.HashMap;
import java.util.stream.Collectors;

/*
    The Inventory class contains the data-structures that hold the 'database': Two hashmaps to contain the ToolTypes
     and Tools. This class declares the hashmaps and fills them with the required (predetermined) data.
 */

public class Inventory {
    private HashMap<String, ToolType> toolTypes;
    private HashMap<String, Tool> tools;

    private void createToolTypes() {
        // create the tool types.
        toolTypes = new HashMap<>();
        toolTypes.put("Chainsaw",
                new ToolType("Chainsaw", BigDecimal.valueOf(1.49), true, false, true));
        toolTypes.put("Ladder",
                new ToolType("Ladder", BigDecimal.valueOf(1.99), true, true, false));
        toolTypes.put("Jackhammer",
                new ToolType("Jackhammer", BigDecimal.valueOf(2.99), true, false, false));
    }

    private void createTools() {
        // create the tools
        tools = new HashMap<>();
        tools.put("CHNS", new Tool("CHNS", getToolType("Chainsaw"), "Stihl"));
        tools.put("LADW", new Tool("LADW", getToolType("Ladder"), "Werner"));
        tools.put("JAKD", new Tool("JAKD", getToolType("Jackhammer"), "DeWalt"));
        tools.put("JAKR", new Tool("JAKR", getToolType("Jackhammer"), "Ridgid"));
    }

    private void createInventory() {
        createToolTypes();
        createTools();
    }
    public Inventory() {
        createInventory();
    }

    // create a sorted view of the inventory
    public String getInventoryForDisplayAsString() {
        return tools.values()
                .stream()
                .sorted()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

    public Tool getTool(String toolCode) {
        return tools.get(toolCode); // could return null
    }

    public ToolType getToolType(String toolTypeName) {
        return toolTypes.get(toolTypeName); // could return null
    }

}
