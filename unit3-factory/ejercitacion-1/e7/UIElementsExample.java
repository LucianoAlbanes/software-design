package e7;

public class UIElementsExample {
    public static void main(String[] args) {
        // Creators:
        UICreator desktop = new DesktopUICreator();
        UICreator mobile =  new MobileUICreator();
        
        
        // foreach
        UICreator[] creators = {desktop, mobile};

        for (UICreator creator : creators) {
            System.out.println();

            Button btn =    creator.createButton();
            TextField txt = creator.createTextField();

            System.out.println(btn.render());
            btn.onClick();

            txt.setTxt("Hello!");
            System.out.println(txt.render());
        
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

interface TextField extends Renderable{
    public void setTxt(String txt);
}


class DesktopButton implements Button {
    @Override 
    public String render() {
        return "[Desktop Oscuro Button]";
    }

    @Override
    public void onClick() {
        System.out.println("Click en " + this.getClass().getSimpleName());
    }
}

class MobileButton implements Button {
    @Override 
    public String render() {
        return "[Mobile Minimalista Button]";
    }

    @Override
    public void onClick() {
        System.out.println("Click en " + this.getClass().getSimpleName());
    }
}

class DesktopTextField implements TextField {
    String txt;

    @Override 
    public String render() {
        return "[Desktop Oscuro TextField] " + txt;
    }

    @Override 
    public void setTxt(String txt) {
        this.txt = txt;
    }
}

class MobileTextField implements TextField {
    String txt;

    @Override
    public String render() {
        return "[Mobile Minimalista TextField] " + txt;
    }

    @Override 
    public void setTxt(String txt) {
        this.txt = txt;
    }
}




// Creators
abstract class UICreator {
    abstract Button createButton();
    abstract TextField createTextField();
}

class DesktopUICreator extends UICreator {
    @Override 
    public Button createButton() {
        return new DesktopButton();
    }

    @Override 
    public TextField createTextField() {
        return new DesktopTextField();
    }
}

class MobileUICreator extends UICreator {
    @Override
    public Button createButton() {
        return new MobileButton();
    }

    @Override
    public TextField createTextField() {
        return new MobileTextField();
    }
}

