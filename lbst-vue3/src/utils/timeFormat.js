export function dateFormat(date) {
  const year = date.getFullYear(); // 获取四位数的年份
  const month = (date.getMonth() + 1).toString().padStart(2, "0"); // 获取月份，加1因为getMonth()返回的是从0开始的
  const day = date.getDate().toString().padStart(2, "0"); // 获取日期，并确保是两位数
  const hours = date.getHours().toString().padStart(2, "0"); // 获取小时，并确保是两位数
  const minutes = date.getMinutes().toString().padStart(2, "0"); // 获取分钟，并确保是两位数
  const seconds = date.getSeconds().toString().padStart(2, "0"); // 获取秒，并确保是两位数
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}
