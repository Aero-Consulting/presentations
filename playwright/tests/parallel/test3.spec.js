const {test, expect} = require('@playwright/test');

test.describe.parallel('parallelization between @files', () => {
    test('first test', async ({
                                  page
                              }) => {
        await page.goto('/');
        await page.locator('.navbar__item.navbar__link >> text="Docs"').click();
        await expect(page).toHaveURL('/docs/intro');

    });
    test('second test', async ({
                                   page
                               }) => {
        await page.goto('/');
        await page.locator('text="Get started"').click();
        let locator = page.locator('header h1');
        await expect(locator).toContainText('Getting started');

    });
});
