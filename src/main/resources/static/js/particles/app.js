/*说明文档:http://www.jq22.com/jquery-info9838*/
particlesJS('particles-js',
  
  {
    "particles": {
      "number": {
        "value": 80,//粒子数值
        "density": {
          "enable": true,//粒子数密度启用
          "value_area": 800//粒子数密度值区域
        }
      },
      "color": {
        "value": "#F0AD4E"//粒子颜色
      },
      "shape": {
        "type": "circle",//粒子形状类型"circle"  "edge"  "triangle"  "polygon"  "star"  "image"
        "stroke": {
          "width": 0,
          "color": "#000000"
        },
        "polygon": {
          "nb_sides": 5//粒子的宽度
        },
        "image": {
          "src": "img/github.svg",
          "width": 100,
          "height": 100
        }
      },
      "opacity": {
        "value": 0.9,//粒子不透明度值
        "random": false,//粒子不透明度随机
        "anim": {
          "enable": false,
          "speed": 1,
          "opacity_min": 0.1,
          "sync": false
        }
      },
      "size": {
        "value": 5,
        "random": true,
        "anim": {
          "enable": false,
          "speed": 40,
          "size_min": 0.1,
          "sync": false
        }
      },
      "line_linked": {
        "enable": true,
        "distance": 150,
        "color": "#ffffff",
        "opacity": 0.4,
        "width": 1
      },
      "move": {
        "enable": true,//粒子移动启用
        "speed": 6,//粒子移动速度
        "direction": "none",//粒子移动方向
        "random": false,//粒子移动随机
        "straight": false,//粒子直接移动
        "out_mode": "out",
        "attract": {
          "enable": false,//粒子移动反弹
          "rotateX": 600,
          "rotateY": 1200
        }
      }
    },
    "interactivity": {
      "detect_on": "canvas",
      "events": {
        "onhover": {
          "enable": true,
          "mode": "repulse"
        },
        "onclick": {
          "enable": true,
          "mode": "push"
        },
        "resize": true
      },
      "modes": {
        "grab": {
          "distance": 400,
          "line_linked": {
            "opacity": 1
          }
        },
        "bubble": {
          "distance": 400,
          "size": 40,
          "duration": 2,
          "opacity": 8,
          "speed": 3
        },
        "repulse": {
          "distance": 200
        },
        "push": {
          "particles_nb": 4
        },
        "remove": {
          "particles_nb": 2
        }
      }
    },
    "retina_detect": true,
    "config_demo": {
      "hide_card": false,
      "background_color": "#b61924",
      "background_image": "",
      "background_position": "50% 50%",
      "background_repeat": "no-repeat",
      "background_size": "cover"
    }
  }

);