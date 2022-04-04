const {test, expect} = require('@playwright/test');

test.describe.parallel('parallelization inside single file', () => {
    test('runs in parallel 1', async ({
                                          page
                                      }) => {
        await page.goto('/');
        const title = page.locator('.navbar__inner .navbar__title');
        //await page.waitForTimeout(5000);
        await expect(title).toHaveText('Playwright');

    });
    test('runs in parallel 2', async ({
                                          page
                                      }) => {
        await page.goto('/');
        const menu = page.locator('.navbar__items:nth-child(1)');
        //await page.waitForTimeout(5000);
        await expect(menu).toContainText('Docs');
    });
    test('runs in parallel 3', async ({
                                               page
                                           }) => {
        await page.goto('/');
        const star = page.locator('.footer__copyright');
        //await page.waitForTimeout(5000);
        await expect(star).toContainText('Microsoft');

    });
    test('runs in parallel 4', async ({
                                          page
                                      }) => {
        await page.goto('/');
        const gettingStared = page.locator('.col.footer__col ul li a').first();
        //await page.waitForTimeout(5000);
        await expect(gettingStared).toHaveText('Getting started');

    });
});

