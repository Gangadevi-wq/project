import java.io.IOException;
import java.util.Random;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/GuessTheNumberServlet")
public class GuessTheNumberServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer secretNumber = (Integer) session.getAttribute("secretNumber");
        Integer attempts = (Integer) session.getAttribute("attempts");
        String message="";

        if (session.isNew() || secretNumber == null) {
            secretNumber = new Random().nextInt(100);
            session.setAttribute("secretNumber", secretNumber);
            attempts = 0;
        }

        int guess = Integer.parseInt(request.getParameter("guess"));
        attempts++;
        System.out.println("secretName"+secretNumber);
        if (guess < secretNumber) {
            message = "Too low! Try again.";
        } else if (guess > secretNumber) {
            message = "Too high! Try again.";
        } else {
            message = "Congratulations! You guessed it right in " + attempts + " attempts.";
            //session.invalidate(); // Invalidate the session after game is over
        }

        session.setAttribute("attempts", attempts); // Only set attempts if session is valid

        request.setAttribute("message", message);
        request.getRequestDispatcher("./result.jsp").forward(request, response);
        System.out.println("Message"+message);
    }
}