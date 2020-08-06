import jaxb.schema.generated.SDMItem;

public class SDKItem {
    private Integer serialNumber;
    private String name;
    TypeOfMeasure typeToMeasureBy;

    public enum TypeOfMeasure{
        Quantity("Quantity"),
        Weight("Weight");

        TypeOfMeasure(String meaning)
        {
            this.meaning = meaning;
        }

        private String meaning;

        public static TypeOfMeasure convertStringToEnum(String meaning)
        {
            if(Quantity.meaning.toLowerCase().equals(meaning.toLowerCase()))
            {
                return Quantity;
            }
            else if(Weight.meaning.toLowerCase().equals(meaning.toLowerCase()))
            {
                return Weight;
            }
            else
            {
                return null;
            }
        }

        public String getMeaning() {
            return meaning;
        }

    }

    SDKItem(Integer serialNumber, String name, TypeOfMeasure itemPurchaseCategory)
    {
        this.serialNumber = serialNumber;
        this.name = name;
        this.typeToMeasureBy = itemPurchaseCategory;
    }

    public SDKItem(SDMItem item)
    {
        this.serialNumber = item.getId();
        this.name = item.getName();
        String itemPurchaseCategoryStr = item.getPurchaseCategory();
        SDKItem.TypeOfMeasure itemPurchaseCategory = SDKItem.TypeOfMeasure.convertStringToEnum(itemPurchaseCategoryStr);
        this.typeToMeasureBy = itemPurchaseCategory;
    }
    public String getName()
    {
        return name;
    }

    public Integer getSerialNumber()
    {
        return this.serialNumber;
    }

    public String getTypeOfMeasureStr()
    {
        return typeToMeasureBy.getMeaning();
    }

    public TypeOfMeasure getTypeOfMeasure()
    {
        return typeToMeasureBy;
    }

}
