package e1;

public class ProfileExample {
    public static void main(String[] args) {
        // CREATORS
        ProfileCreator studentCreator = new StudentProfileCreator();
        ProfileCreator teacherCreator = new TeacherProfileCreator();

        // TEST
        Profile student = studentCreator.createProfile();
        Profile teacher = teacherCreator.createProfile();

        System.out.println(student.getRole() + ": " + student.getPermissions());
        System.out.println(teacher.getRole() + ": " + teacher.getPermissions());
    }
}


// Interface

interface Profile {
    String getRole();
    String getPermissions();
}

class StudentProfile implements Profile {
    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public String getPermissions() {
        return "view courses, submit assignments";
    }
}

class TeacherProfile implements Profile {
    @Override
    public String getRole() {
        return "Teacher";
    }

    @Override
    public String getPermissions() {
        return "view courses, grade assignments, edit courses";
    }
}

// Creators

abstract class ProfileCreator {
    public abstract Profile createProfile();
}

class StudentProfileCreator extends ProfileCreator {
    @Override
    public Profile createProfile() {
        return new StudentProfile();
    }
}

class TeacherProfileCreator extends ProfileCreator {
    @Override
    public Profile createProfile() {
        return new TeacherProfile();
    }
}
