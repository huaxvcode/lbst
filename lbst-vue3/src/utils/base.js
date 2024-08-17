export function getBaseUrl() {
  return "http://localhost:8090";
}

export function getHeaders() {
  const tokenName = localStorage.getItem("tokenName");
  const tokenValue = localStorage.getItem("tokenValue");
  const headers = {}
  headers[tokenName] = tokenValue;
  // console.log(headers);
  return headers;
};
