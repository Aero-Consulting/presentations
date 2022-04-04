const {test, expect} = require('@playwright/test');

test('plain test @plain', async ({
                                     page
                                 }) => {
    await page.goto('/');
    const title = page.locator('.navbar__inner .navbar__title');
    //await page.waitForTimeout(5000);
    await expect(title).toHaveText('Playwright');
    // multiple text locator
    //await page.locator('text=API').click();
    await page.locator('.navbar__item.navbar__link >> text="API"');
});
