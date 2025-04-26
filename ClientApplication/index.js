console.log('Thank you for using WebStorm 💙')

const clientId = 'client1';

console.log(window.location.origin);

function checkInput() {
    const serverUrl = document.getElementById('serverUrl').value.trim();
    const loginButton = document.getElementById('loginButton');
    loginButton.disabled = serverUrl === '';
}

function startOAuth() {
    const serverUrl = document.getElementById('serverUrl').value.trim();
    if (serverUrl === '') {
        return;
    }

    localStorage.setItem('serverUrl', serverUrl);

    console.log(localStorage.getItem('serverUrl'))

    const redirectURL = `${window.location.origin}/callback.html`;
    const state = 'xyz123';
    const scope = 'profile';
    const responseType = 'code';

    const url = `${serverUrl}/authorize?responseType=${responseType}&redirectURL=${redirectURL}&scope=${scope}&state=${state}&clientID=${clientId}`;
    window.location.href = url;
}

async function getToken() {

    console.log(localStorage.getItem('serverUrl'))


    const serverUrl = localStorage.getItem('serverUrl') || '';

    if (serverUrl === '') {
        document.getElementById("tokenDisplay").textContent = `Error: serverUrl is missing`;
        return;
    }



    const payload = {
        code: code,
        clientId: clientId,
        clientSecret: "password",
        clientURL: window.location.origin
    };



    try {
        const response = await fetch(serverUrl + "/token", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        const data = await response.json();

        if (response.ok) {
            document.getElementById("tokenDisplay").textContent = data.accessToken;
        } else {
            document.getElementById("tokenDisplay").textContent = `Error: ${JSON.stringify(data)}`;
        }

    } catch (error) {
        document.getElementById("tokenDisplay").textContent = `Error: ${error}`;
    }

}

function exitToLogin() {
    localStorage.clear();
    window.location.href = '/login.html';
}


