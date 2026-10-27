package library;

import java.util.Objects;

class Member {
    private final int id;
    private final String name;
    private final MemberType type;

    Member(int id, String name, MemberType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    MemberType getType() {
        return type;
    }

    boolean canBorrow(int currentLoans) {
        return currentLoans < type.maxLoans();
    }

    @Override
    public String toString() {
        return "#" + id + " " + name + " (" + type + ", max " + type.maxLoans() + " books)";
    }


    @Override

    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (!(obj instanceof Member)) return false;

        Member other = (Member) obj;

        return id == other.id;

    }

    @Override

    public int hashCode() {

        return Objects.hash(id);

    }

}
