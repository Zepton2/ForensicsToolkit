package LastAccessed;

/**
 * Created by Jarred on 1/20/2016.
 * This class contains static functions for formatting purposes
 */
public class Formatting
{
    public static String formatDate(String date)
    {
        String month = null;
        String[] dateFormat = date.split("\\ ");//splits the string by a space

        switch(dateFormat[1])//coverts month to numerical
        {
            case "Jan":
                month = "01";
                break;
            case "Feb":
                month = "02";
                break;
            case "Mar":
                month = "03";
                break;
            case "Apr":
                month = "04";
                break;
            case "May":
                month = "05";
                break;
            case "Jun":
                month = "06";
                break;
            case "Jul":
                month = "07";
                break;
            case "Aug":
                month = "08";
                break;
            case "Sep":
                month = "09";
                break;
            case "Oct":
                month = "10";
                break;
            case "Nov":
                month = "11";
                break;
            case "Dec":
                month = "12";
                break;
            default:
                month = "00";
        }

        date = month + "/" +dateFormat[2] + "/" + dateFormat[5];//catenates the date

        return date;
    }

    public static String lastAccessed(String date)
    {
        String year = " ";
        String month = " ";
        String day = " ";
        String[] time = date.split("(?<=\\G.{1})");//splits string into array by each character
        year = time[0] + time[1] + time[2] + time[3];//adds year
        month = time[5] + time[6];//adds month
        day = time[8] + time[9];//adds day
        date = month + "/" + day + "/" + year;//joins the strings together
        return date;
    }

    public static boolean compareDate(String currentDate, String accessedDate)
    {
        if(currentDate.equals(accessedDate))//compares the two dates to make sure they are the same
        {
            return true;
        }
        return false;
    }
}
