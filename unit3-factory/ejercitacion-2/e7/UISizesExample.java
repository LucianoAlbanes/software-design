package e7;

public class UISizesExample {
    public static void main(String[] args) {
        // Creators:
        UICreator desktop = new DesktopUICreator();
        UICreator mobile =  new MobileUICreator();


        // foreach
        UICreator[] creators = {desktop, mobile};

        for (UICreator creator : creators) {
            System.out.println();

            Button btn =  creator.createButton();
            Label label = creator.createLabel();

            System.out.println(btn.render());
            btn.onClick();

            label.setTxt("Hello!");
            System.out.println(label.render());

        }
    }
}





// Interfaces

interface Renderable {
    public String render();
}

interface Button extends Renderable {
    public void onClick();
}

interface Label extends Renderable {
    public void setTxt(String txt);
}


class DesktopButton implements Button {
    @Override
    public String render() {
        return "[Desktop Button] size 1920x1080px";
    }

    @Override
    public void onClick() {
        System.out.println("Click en " + this.getClass().getSimpleName());
    }
}

class MobileButton implements Button {
    @Override
    public String render() {
        return "[Mobile Button] size 1080x2400px";
    }

    @Override
    public void onClick() {
        System.out.println("Click en " + this.getClass().getSimpleName());
    }
}

class DesktopLabel implements Label {
    String txt;

    @Override
    public String render() {
        return "[Desktop Label] size 1920x1080px " + txt;
    }

    @Override
    public void setTxt(String txt) {
        this.txt = txt;
    }
}

class MobileLabel implements Label {
    String txt;

    @Override
    public String render() {
        return "[Mobile Label] size 1080x2400px " + txt;
    }

    @Override
    public void setTxt(String txt) {
        this.txt = txt;
    }
}




// Creators
abstract class UICreator {
    abstract Button createButton();
    abstract Label createLabel();
}

class DesktopUICreator extends UICreator {
    @Override
    public Button createButton() {
        return new DesktopButton();
    }

    @Override
    public Label createLabel() {
        return new DesktopLabel();
    }
}

class MobileUICreator extends UICreator {
    @Override
    public Button createButton() {
        return new MobileButton();
    }

    @Override
    public Label createLabel() {
        return new MobileLabel();
    }
}

