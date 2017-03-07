package it.inelemento.examples.symfony;

import net.continuumsecurity.Config;
import net.continuumsecurity.Credentials;
import net.continuumsecurity.UserPassCredentials;
import net.continuumsecurity.behaviour.ILogin;
import net.continuumsecurity.behaviour.ILogout;
import net.continuumsecurity.behaviour.INavigable;
import net.continuumsecurity.web.WebApplication;
import org.openqa.selenium.By;

public class SymfonyApplication extends WebApplication implements ILogin,
        ILogout,INavigable {
	public SymfonyApplication() {
        super();

    }

    @Override
    public void openLoginPage() {
        driver.get(Config.getInstance().getBaseUrl() + "login");
        findAndWaitForElement(By.id("username"));
    }

    @Override
    public void login(Credentials credentials) {
        UserPassCredentials creds = new UserPassCredentials(credentials);
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys(creds.getUsername());
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(creds.getPassword());
        driver.findElement(By.name("_submit")).click();
    }

    // Convenience method
    public void login(String username, String password) {
        login(new UserPassCredentials(username, password));
    }

    @Override
    public boolean isLoggedIn() {
        driver.get(Config.getInstance().getBaseUrl());
        if (driver.getPageSource().contains("Benvenuto")) {
            return true;
        } else {
            return false;
        }
    }

    public void viewProfile() {
        driver.findElement(By.linkText("Profilo")).click();
    }

    @Override
    public void logout() {
        driver.get(Config.getInstance().getBaseUrl() + "logout");
    }

    public void search(String query) {
        findAndWaitForElement(By.linkText("Tasks")).click();
        driver.findElement(By.id("q")).clear();
        driver.findElement(By.id("q")).sendKeys(query);
        driver.findElement(By.id("search")).click();
    }

    public void navigate() {
        openLoginPage();
        login(Config.getInstance().getDefaultCredentials());
        viewProfile();
        //search("test");
    }
}
