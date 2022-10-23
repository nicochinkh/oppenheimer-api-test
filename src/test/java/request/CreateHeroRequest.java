package request;

public class CreateHeroRequest {

    private String natid;
    private String name;
    private String gender;
    private String birthDate;
    private String deathDate;
    private String browniePoints;
    private String salary;
    private String taxPaid;

    public CreateHeroRequest(String natid, String name, String gender, String birthDate,
                             String deathDate, String browniePoints, String salary, String taxPaid) {
        this.natid = natid;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.browniePoints = browniePoints;
        this.salary = salary;
        this.taxPaid = taxPaid;
    }

    public String getNatid() {
        return natid;
    }

    public void setNatid(String natid) {
        this.natid = natid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public String getBrowniePoints() {
        return browniePoints;
    }

    public void setBrowniePoints(String browniePoints) {
        this.browniePoints = browniePoints;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getTaxPaid() {
        return taxPaid;
    }

    public void setTaxPaid(String taxPaid) {
        this.taxPaid = taxPaid;
    }
}
