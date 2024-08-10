export function isUsernameValid(username) {
    const pattern = /^[a-zA-Z0-9_]{3,20}$/;
    return pattern.test(username);
}

export function isPasswordValid(password) {
    const pattern = /^[a-zA-Z0-9_@$!%*?&#]{3,20}$/;
    return pattern.test(password);
}

export function isCaptchaValid(code) {
    const pattern = /^[a-zA-Z0-9]{4}$/;
    return pattern.test(code);
}