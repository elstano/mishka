package ru.org.icad.mishka.app.chemistry;

public class Chemistry implements Comparable<Chemistry> {

    private double contentFe;
    private double contentSi;
    private double contentCu;
    private double contentMg;
    private double contentMn;
    private double contentTi;

    public Chemistry(
            double contentFe,
            double contentSi,
            double contentCu,
            double contentMg,
            double contentMn,
            double contentTi
    ) {
        this.contentFe = contentFe;
        this.contentSi = contentSi;
        this.contentCu = contentCu;
        this.contentMg = contentMg;
        this.contentMn = contentMn;
        this.contentTi = contentTi;
    }

    public double getContentFe() {
        return contentFe;
    }

    public double getContentSi() {
        return contentSi;
    }

    public double getContentCu() {
        return contentCu;
    }

    public double getContentMg() {
        return contentMg;
    }

    public double getContentMn() {
        return contentMn;
    }

    public double getContentTi() {
        return contentTi;
    }

    public boolean isSuit(Chemistry chemistry) {
        return contentCu >= chemistry.getContentCu()
                && contentFe >= chemistry.getContentFe()
                && contentMg >= chemistry.getContentMg()
                && contentMn >= chemistry.getContentMn()
                && contentSi >= chemistry.getContentSi()
                && contentTi >= chemistry.getContentTi();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chemistry chemistry = (Chemistry) o;

        return Double.compare(chemistry.contentCu, contentCu) == 0
                && Double.compare(chemistry.contentFe, contentFe) == 0
                && Double.compare(chemistry.contentMg, contentMg) == 0
                && Double.compare(chemistry.contentMn, contentMn) == 0
                && Double.compare(chemistry.contentSi, contentSi) == 0
                && Double.compare(chemistry.contentTi, contentTi) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(contentFe);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(contentSi);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(contentCu);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(contentMg);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(contentMn);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(contentTi);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public int compareTo(Chemistry chemistry) {
        if (contentCu > chemistry.getContentCu()
                && contentFe > chemistry.getContentFe()
                && contentMg > chemistry.getContentMg()
                && contentMn > chemistry.getContentMn()
                && contentSi > chemistry.getContentSi()
                && contentTi > chemistry.getContentTi()) {
            return -1;
        }

        if (contentCu == chemistry.getContentCu()
                && contentFe == chemistry.getContentFe()
                && contentMg == chemistry.getContentMg()
                && contentMn == chemistry.getContentMn()
                && contentSi == chemistry.getContentSi()
                && contentTi == chemistry.getContentTi()) {
            return 0;
        }

        return 1;
    }

    @Override
    public String toString() {
        return "Chemistry{" +
                "contentFe=" + contentFe +
                ", contentSi=" + contentSi +
                ", contentCu=" + contentCu +
                ", contentMg=" + contentMg +
                ", contentMn=" + contentMn +
                ", contentTi=" + contentTi +
                '}';
    }
}
