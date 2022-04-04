const {test, expect} = require('@playwright/test');

test.describe('parallelization between @files', () => {
    test('first test', async ({
                                  page
                              }) => {
        await page.goto('/');
        const rightNavbar = page.locator('.navbar__items--right');
        //await page.waitForTimeout(5000);
        await expect(rightNavbar.locator('a')).toHaveAttribute('href', 'https://github.com/microsoft/playwright');

    });
    test('second test', async ({
                                   page
                               }) => {
        await page.goto('/');
        const menu = page.locator('.navbar__items:nth-child(1)');
        //await page.waitForTimeout(5000);
        await expect(menu).toContainText('Docs');

    });
});
