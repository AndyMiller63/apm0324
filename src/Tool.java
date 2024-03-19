

// - Tools, History(?),
// Refund

// The Tool class is use to represent a tool available for rent.
// The ToolType class defines the cost per days, etc.
// See Inventory for creation.

public class Tool implements Comparable<Tool> {
    private String toolCode;
    private ToolType toolType;
    private String brand;

    // Constructor
    public Tool(String toolCode, ToolType toolType, String brand) {
        setToolCode(toolCode);
        setToolType(toolType);
        setBrand(brand);
    }

    // Getters and Setters
    public String getToolCode() {
        return toolCode;
    }

    private void setToolCode(String toolCode) {
        if (toolCode == null || toolCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Tool code cannot be null or empty.");
        }
        this.toolCode = toolCode;
    }

    public ToolType getToolType() {
        return toolType;
    }

    private void setToolType(ToolType toolType) {
        if (toolType == null) {
            throw new IllegalArgumentException("Tool type cannot be null.");
        }
        this.toolType = toolType;
    }

    public String getBrand() {
        return brand;
    }

    private void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Brand cannot be null or empty.");
        }
        this.brand = brand;
    }

    // toString used for creating rental agreements
    @Override
    public String toString() {
        return String.format("Code %s: %s %s", toolCode, brand, toolType);
    }

    @Override
    public int compareTo(Tool o) {
        return getToolCode().compareTo(o.getToolCode());
    }

}