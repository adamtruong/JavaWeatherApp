import javax.imageio.ImageIO;
import javax.swing.*;

import org.json.simple.JSONObject;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.File;

public class WeatherAppGui extends JFrame {
    private JSONObject weatherData;

    public WeatherAppGui() {
        //setup gui and add title
        super("Weather App");

        //configure gui to end the program's once it has been closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set size of the gui
        setSize(450, 650);

        //load the gui at the center of the screen
        setLocationRelativeTo(null);

        //make the layout manager null to manually position components
        setLayout(null);

        //prevent resize of gui
        setResizable(false);

        addGuiComponents(); 
    }

    private void addGuiComponents() {
        //search field
        JTextField searchTextField = new JTextField();

        //set location and size of component
        searchTextField.setBounds(15, 15, 351, 45);

        //change the font style and size
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(searchTextField);

        //weather image
        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png")); 
        weatherConditionImage.setBounds(0, 125, 450, 217);
        add(weatherConditionImage);

        //temperature label
        JLabel temperatureText = new JLabel("10°C");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));

        //center the temperature text
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        //weather description
        JLabel weatherDescriptionText = new JLabel("Cloudy");
        weatherDescriptionText.setBounds(0, 405, 450, 36);
        weatherDescriptionText.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherDescriptionText.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherDescriptionText);

        //humidity image
        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(15, 500, 74, 66);
        add(humidityImage);

        //humidity text
        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100% </html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

        //windspeed image
        JLabel windspeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        windspeedImage.setBounds(220, 500, 74, 66);
        add(windspeedImage);

        //windspeed text
        JLabel windspeedText = new JLabel("<html><b>Windspeed</b> 15km/h </html>");
        windspeedText.setBounds(310, 500, 85, 55);
        windspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windspeedText);

        //search button
        JButton searchButton = new JButton(loadImage("src/assets/search.png"));

        //change the cursor to hand cursor when hovering search button
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 47, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get location from user
                String userInput = searchTextField.getText();
                
                //validate input
                if (userInput.replaceAll("\\s", "").length() <= 0) {
                    return;
                }

                //retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);

                //update gui

                //update weather image
                String weatherCondition = (String) weatherData.get("weather_condition");

                //update weather image to current weather condition
                switch (weatherCondition) {
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src/assets/clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src/assets/cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src/assets/rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src/assets/snow.png"));
                        break;
                }

                //update temperature text
                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + "°C");

                //update weather description
                weatherDescriptionText.setText(weatherCondition);

                //update humidity text
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "% </html>");

                //update windspeed text
                double windspeed = (double) weatherData.get("windspeed");
                windspeedText.setText("<html><b>Windspeed</b> " + windspeed + "km/h </html>");
            }
        });
        add(searchButton);
    }

    //Create images in gui components
    private ImageIcon loadImage(String resourcePath) {
        try{
            //read the image file from the resource folder
            BufferedImage image = ImageIO.read(new File(resourcePath));

            //return the image
            return new ImageIcon(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Image not found: " + resourcePath);
        return null;
    }
}
