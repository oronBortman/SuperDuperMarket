public class SDKItem {
    private Integer serialNumber;
    private String name;
    TypeOfMeasure typeToMeasureBy;

    public enum TypeOfMeasure{
        Quantity("Quantity") {
            int val;
            public void setVal(int val)
            {
                this.val=val;
            }
        },
        Weight("Weight") {
            float val;
            public void setVal(float val)
            {
                this.val=val;
            }
        };

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
    public String getName()
    {
        return name;
    }

    public Integer getSerialNumber()
    {
        return this.serialNumber;
    }

    public String getTypeOfMeasure()
    {
        return typeToMeasureBy.getMeaning();
    }

}
