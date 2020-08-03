public class SDKItem {
    private String serialNumber;
    private String name;
    TypeOfMeasure typeToMeasureBy;

    enum TypeOfMeasure{
        Quantity("Quantity") {
            int val;
            },
        Weight("Weight") {
            float val;
        };

        TypeOfMeasure(String meaning)
        {
            this.meaning = meaning;
        }

        private String meaning;

        public String getMeaning() {
            return meaning;
        }
    }
    public String getName()
    {
        return name;
    }

    public String getSerialNumber()
    {
        return this.serialNumber;
    }

    public String getTypeOfMeasure()
    {
        return typeToMeasureBy.getMeaning();
    }

}
