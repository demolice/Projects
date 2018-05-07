public class Textbox {
   public int X = 0, Y = 0, H = 28, W = 200;
   public int TEXTSIZE = 24;
   
   // COLORS
   public color Background = color(26, 0, 69);
   public color Foreground = color(255);
   public color BackgroundSelected = color(26, 0, 13);
   public color Border = color(30, 30, 30);
   
   public boolean BorderEnable = false;
   public int BorderWeight = 1;
   
   public String Text = "";
   public int TextLength = 0;
   
   public String name = "";

   private boolean selected = false;
   
   Textbox() {
      // CREATE OBJECT DEFAULT Textbox
   }
   
   Textbox(int x, int y, int w, int h) {
      X = x; Y = y; W = w; H = h;
   }
   
   void tDraw() {
      
      // DRAWING THE BACKGROUND
      if (selected) {
         fill(BackgroundSelected);
      } else {
         fill(Background);
      }
      
      if (BorderEnable) {
         strokeWeight(BorderWeight);
         stroke(Border);
      } else {
         noStroke();
      }
      
      rectMode(CORNER);
      rect(X, Y, W, H);
      
      // DRAWING THE TEXT ITSELF
      fill(Foreground);
      textSize(TEXTSIZE);
      text(Text, X + (textWidth("a") / 2), Y + TEXTSIZE);
   }
   
   // IF THE KEYCODE IS ENTER RETURN 1
   // ELSE RETURN 0
   boolean tKeyPressed(char KEY, int KEYCODE) {
      if (selected) {
         if (KEYCODE == (int)BACKSPACE) {
            backspace();
            return true;
         } else if (KEYCODE == 32) {
            // SPACE
            //addText(' ');
         } else if (KEYCODE == (int)ENTER) {
            return true;
         } else {
            // CHECK IF THE KEY IS A LETTER OR A NUMBER
            //boolean isKeyCapitalLetter = (KEY >= 'A' && KEY <= 'Z');
            //boolean isKeySmallLetter = (KEY >= 'a' && KEY <= 'z');
            boolean isKeyNumber = (KEY >= '0' && KEY <= '9');
            boolean isDot = (KEY == '.');
      
            if (isDot || isKeyNumber) {
               addText(KEY);
               return true;
            }
         }
      }
    
      return false;
   }
   
   private void addText(char text) {
      // IF THE TEXT WIDHT IS IN BOUNDARIES OF THE Textbox
      if (textWidth(Text + text) < W) {
         Text += text;
         TextLength++;
      }
   }
   
   public void addStartUpText(String text) {
     TextLength = text.length();
     this.Text += text;
   }
   
   public void repairSize() {
     TextLength = Text.length();
   }
   
   private void backspace() {
      if (TextLength - 1 >= 0) {
         Text = Text.substring(0, TextLength - 1);
         TextLength--;
      }
   }
   
   // FUNCTION FOR TESTING IS THE POINT
   // OVER THE Textbox
   public boolean overBox(int x, int y) {
      if (x >= X && x <= X + W) {
         if (y >= Y && y <= Y + H) {
            return true;
         }
      }
      
      return false;
   }
   
   void pressed(int x, int y) {
      if (overBox(x, y)) {
         selected = true;
      } else {
         selected = false;
      }
   }
}
