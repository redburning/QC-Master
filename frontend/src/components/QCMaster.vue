<template>
  <div style="display: flex; gap: 0px;" @click="collapseSampleSelectionDropdownMenu=false; collapseLandmarkSelectionDropdownMenu=false">
    <div :style="{ width: layout.sidebar.width + 'px', height: layout.sidebar.height + 'px' }" style="border-right:1px solid rgba(36, 41, 46, 0.12)"
      v-show="viewMode==='default'">
      <img src="@/assets/imgs/logo.png" height="40px" style="padding-left: 5px; padding-top: 5px;">
      <div id="progress" class="sample-list-container">
        <ul style="list-style-type:none; padding-left:5px; margin-top: 0px;">
          <li v-for="(sample, index) in monitorTask.monitorSamples" :key="sample" style="display: flex;"
            :class="{ 'sample-list-item': sample.indexOf('QC') === -1, 'sample-list-item-qc': sample.indexOf('QC') !== -1 }">
            <el-button style="border: none; background-color: rgba(0,0,0,0); color: white; padding: 0px; font-size: 13px; cursor: pointer; display: flex; width: 100%; align-items: center;" @click="onSelectedSample(sample)" @dblclick.native="onDBClickSample(sample)">
              <svg t="1694444178129" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="13049" width="12" height="12">
                <path d="M567.168 115.498667l34.901333 34.901333L141.568 610.901333C105.301333 647.168 85.333333 695.381333 85.333333 746.666667s19.968 99.498667 56.234667 135.808C177.877333 918.698667 226.090667 938.666667 277.333333 938.666667s99.456-19.968 135.765334-56.234667L873.6 421.930667l34.901333 34.901333 60.330667-60.330667-341.333333-341.333333-60.330667 60.330667z m135.765333 356.437333l-102.528-102.570667-60.330666 60.330667 102.528 102.570667-60.330667 60.373333-42.24-42.24-60.330667 60.330667 42.24 42.24-60.373333 60.373333-102.528-102.570667L298.666667 671.061333l102.528 102.570667-48.469334 48.469333c-40.32 40.277333-110.506667 40.277333-150.826666 0C181.76 801.92 170.666667 775.168 170.666667 746.666667s11.093333-55.253333 31.232-75.434667L662.4 210.730667l150.826667 150.826666-110.293334 110.378667z" p-id="13050" fill="white"></path>
              </svg>
              {{ index + 1 + ': ' + sample }}
            </el-button>
          </li>
        </ul>
      </div>
    </div>
    <div style="background-color: #f4f5f5;">
      <div v-show="viewMode!=='hideHeader'" style="display:flex; justify-content:space-between; align-items:center; padding:10px; box-sizing: border-box;"
        :style="{ height: layout.header.height + 'px', width: layout.header.width + 'px' }">
        <div>
          <el-input v-model="monitorTask.monitorPath" size="small" style="width:500px;" :class="{ monitoring: monitorTask.monitoring }">
            <template slot="prepend">
              <el-button type="primary" size="small" icon="el-icon-folder-opened" @click="loadMonitorDirTree" class="simple-btn">Browse</el-button>
            </template>
          </el-input>
        </div>

        <div style="display:flex; align-items: center; gap: 10px;">
          <el-tooltip class="item" effect="dark" content="Recent projects" placement="bottom">
            <el-button size="small" @click="onOpenRecentProjects" class="toolbar-button">
              <svg t="1739326766423" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="7156" width="16" height="16"><path d="M512 629.76c-20.48 0-40.96-5.12-61.44-15.36L46.08 409.6C15.36 394.24 0 363.52 0 327.68c0-35.84 15.36-66.56 46.08-81.92l404.48-204.8c46.08-25.6 87.04-15.36 117.76 0h5.12l404.48 204.8c30.72 15.36 46.08 46.08 46.08 81.92 0 35.84-15.36 66.56-46.08 81.92l-404.48 204.8c-20.48 10.24-40.96 15.36-61.44 15.36zM87.04 332.8l404.48 204.8c15.36 10.24 20.48 10.24 46.08 0l399.36-204.8v-10.24l-399.36-204.8c-20.48-10.24-30.72-10.24-46.08 0l-404.48 204.8v10.24z" fill="#231815" p-id="7157"></path><path d="M512 814.08c-20.48 0-40.96-5.12-61.44-15.36l-404.48-204.8c-20.48-10.24-30.72-35.84-20.48-61.44 10.24-20.48 40.96-30.72 61.44-20.48l404.48 204.8c15.36 10.24 25.6 10.24 46.08 0l404.48-204.8c20.48-10.24 46.08 0 61.44 20.48 10.24 20.48 0 46.08-20.48 61.44l-404.48 204.8c-25.6 10.24-46.08 15.36-66.56 15.36z" fill="#231815" p-id="7158"></path><path d="M512 998.4c-20.48 0-40.96-5.12-61.44-15.36l-404.48-204.8C25.6 768 15.36 737.28 25.6 716.8c10.24-20.48 35.84-30.72 61.44-20.48l404.48 204.8c15.36 10.24 25.6 10.24 46.08 0l404.48-204.8c20.48-10.24 46.08 0 61.44 20.48 10.24 20.48 0 46.08-20.48 61.44l-404.48 204.8c-25.6 10.24-46.08 15.36-66.56 15.36z" fill="#231815" p-id="7159"></path></svg>
            </el-button>
          </el-tooltip>

          <el-tooltip class="item" effect="dark" content="Save project" placement="bottom">
            <el-button size="small" @click="saveProjectDialogVisible = true" class="toolbar-button" :disabled="monitorTask.taskId === null" style="margin-left:0px; display:flex; align-items: center;">
              <span>
                <svg xmlns="http://www.w3.org/2000/svg" data-name="Layer 1" viewBox="0 0 24 24" width="18" height="18" class="css-sr6nr"><path d="M20.71,9.29l-6-6a1,1,0,0,0-.32-.21A1.09,1.09,0,0,0,14,3H6A3,3,0,0,0,3,6V18a3,3,0,0,0,3,3H18a3,3,0,0,0,3-3V10A1,1,0,0,0,20.71,9.29ZM9,5h4V7H9Zm6,14H9V16a1,1,0,0,1,1-1h4a1,1,0,0,1,1,1Zm4-1a1,1,0,0,1-1,1H17V16a3,3,0,0,0-3-3H10a3,3,0,0,0-3,3v3H6a1,1,0,0,1-1-1V6A1,1,0,0,1,6,5H7V8A1,1,0,0,0,8,9h6a1,1,0,0,0,1-1V6.41l4,4Z"></path></svg>
              </span>
            </el-button>
          </el-tooltip>

          <el-tooltip class="item" effect="dark" content="Capture screenshot" placement="top">
            <el-button size="small" @click="captureScreenshot" class="toolbar-button" style="margin-left:0px; display:flex; align-items: center;">
              <span>
                <svg t="1698918645774" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5474" width="18" height="18"><path d="M353.834667 183.168A42.666667 42.666667 0 0 1 384 170.666667h256a42.666667 42.666667 0 0 1 30.165333 12.501333L742.997333 256H853.333333a85.333333 85.333333 0 0 1 85.333334 85.333333v426.666667a85.333333 85.333333 0 0 1-85.333334 85.333333H170.666667a85.333333 85.333333 0 0 1-85.333334-85.333333V341.333333a85.333333 85.333333 0 0 1 85.333334-85.333333h110.336l72.832-72.832zM401.664 256L328.832 328.832A42.666667 42.666667 0 0 1 298.666667 341.333333H170.666667v426.666667h682.666666V341.333333h-128a42.666667 42.666667 0 0 1-30.165333-12.501333L622.336 256H401.664zM512 448a85.333333 85.333333 0 1 0 0 170.666667 85.333333 85.333333 0 0 0 0-170.666667z m-170.666667 85.333333a170.666667 170.666667 0 1 1 341.333334 0 170.666667 170.666667 0 0 1-341.333334 0z" fill="#0D0D0D" p-id="5475"></path></svg>
              </span>
            </el-button>
          </el-tooltip>

          <el-tooltip class="item" effect="dark" content="Settings" placement="bottom">
            <el-button size="small" @click="settingDialogVisible = true" class="toolbar-button" style="margin-left:0px; display:flex; align-items: center;">
              <span>
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="18" height="18" class="css-eyx4do"><path d="M21.32,9.55l-1.89-.63.89-1.78A1,1,0,0,0,20.13,6L18,3.87a1,1,0,0,0-1.15-.19l-1.78.89-.63-1.89A1,1,0,0,0,13.5,2h-3a1,1,0,0,0-.95.68L8.92,4.57,7.14,3.68A1,1,0,0,0,6,3.87L3.87,6a1,1,0,0,0-.19,1.15l.89,1.78-1.89.63A1,1,0,0,0,2,10.5v3a1,1,0,0,0,.68.95l1.89.63-.89,1.78A1,1,0,0,0,3.87,18L6,20.13a1,1,0,0,0,1.15.19l1.78-.89.63,1.89a1,1,0,0,0,.95.68h3a1,1,0,0,0,.95-.68l.63-1.89,1.78.89A1,1,0,0,0,18,20.13L20.13,18a1,1,0,0,0,.19-1.15l-.89-1.78,1.89-.63A1,1,0,0,0,22,13.5v-3A1,1,0,0,0,21.32,9.55ZM20,12.78l-1.2.4A2,2,0,0,0,17.64,16l.57,1.14-1.1,1.1L16,17.64a2,2,0,0,0-2.79,1.16l-.4,1.2H11.22l-.4-1.2A2,2,0,0,0,8,17.64l-1.14.57-1.1-1.1L6.36,16A2,2,0,0,0,5.2,13.18L4,12.78V11.22l1.2-.4A2,2,0,0,0,6.36,8L5.79,6.89l1.1-1.1L8,6.36A2,2,0,0,0,10.82,5.2l.4-1.2h1.56l.4,1.2A2,2,0,0,0,16,6.36l1.14-.57,1.1,1.1L17.64,8a2,2,0,0,0,1.16,2.79l1.2.4ZM12,8a4,4,0,1,0,4,4A4,4,0,0,0,12,8Zm0,6a2,2,0,1,1,2-2A2,2,0,0,1,12,14Z"></path></svg>
              </span>
            </el-button>
          </el-tooltip>

          <div style="position: relative;">
            <el-button size="small" class="toolbar-button" style="padding: 2px 8px;" @click.stop="collapseSampleSelectionDropdownMenu = !collapseSampleSelectionDropdownMenu">
              <div style="display: flex; align-items: center;">
                <div>
                  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 117.8 64" width="24" height="24" class="css-eyx4do"><linearGradient id="a" gradientUnits="userSpaceOnUse" x1="96.44" y1="83.7" x2="96.44" y2="-9.48"><stop offset="0" stop-color="#FFF23A"></stop><stop offset=".04" stop-color="#FEE62D"></stop><stop offset=".12" stop-color="#FED41A"></stop><stop offset=".2" stop-color="#FDC90F"></stop><stop offset=".28" stop-color="#FDC60B"></stop><stop offset=".67" stop-color="#F28F3F"></stop><stop offset=".89" stop-color="#ED693C"></stop><stop offset="1" stop-color="#E83E39"></stop></linearGradient><path d="M15.2 22.7H1.9c-1.1 0-1.9.9-1.9 1.9v37.5C0 63.2.9 64 1.9 64h13.3c1.1 0 1.9-.9 1.9-1.9V24.6c0-1.1-.8-1.9-1.9-1.9zM36.3 10.2H23c-1.1 0-1.9.9-1.9 1.9v50c0 1.1.9 1.9 1.9 1.9h13.3c1.1 0 1.9-.9 1.9-1.9v-50c0-1-.9-1.9-1.9-1.9zM57.3 32H44c-1.1 0-1.9.9-1.9 1.9V62c0 1.1.9 1.9 1.9 1.9h13.3c1.1 0 1.9-.9 1.9-1.9V34c0-1.1-.8-2-1.9-2zM70.1 38V26.1c0-3.4 2.7-6.1 6.1-6.1h4.1V2c0-1.1-.9-1.9-1.9-1.9H65.1c-1.1-.1-2 .8-2 1.9v60.1c0 1.1.9 1.9 1.9 1.9h13.3c1.1 0 1.9-.9 1.9-1.9v-18h-4.1c-3.2 0-6-2.8-6-6.1z"></path><path fill="url(#a)" d="M116.7 24.9H103.6V11.8c0-.6-.5-1.1-1.1-1.1h-12c-.6 0-1.1.5-1.1 1.1v13.1H76.2c-.6 0-1.1.5-1.1 1.1v12c0 .6.5 1.1 1.1 1.1h13.2v13.2c0 .6.5 1.1 1.1 1.1h11.9c.6 0 1.1-.5 1.1-1.1V39.1h13.1c.6 0 1.1-.5 1.1-1.1V26.1c.1-.6-.4-1.2-1-1.2z"></path></svg>
                </div>
                <div style="padding-left:8px; padding-right:4px;">
                  <span style="font-size: 13px; font-weight: 600; color:rgba(36, 41, 46, 0.75)">
                    <span>{{ selectedLastNSamples.label }}</span>
                  </span>
                </div>
                <div>
                  <svg v-show="!collapseSampleSelectionDropdownMenu" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-eyx4do">
                    <path d="M17,9.17a1,1,0,0,0-1.41,0L12,12.71,8.46,9.17a1,1,0,0,0-1.41,0,1,1,0,0,0,0,1.42l4.24,4.24a1,1,0,0,0,1.42,0L17,10.59A1,1,0,0,0,17,9.17Z"></path>
                  </svg>
                  <svg v-show="collapseSampleSelectionDropdownMenu" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-ovd6k">
                    <path d="M17,13.41,12.71,9.17a1,1,0,0,0-1.42,0L7.05,13.41a1,1,0,0,0,0,1.42,1,1,0,0,0,1.41,0L12,11.29l3.54,3.54a1,1,0,0,0,.7.29,1,1,0,0,0,.71-.29A1,1,0,0,0,17,13.41Z"></path>
                  </svg>
                </div>
              </div>
            </el-button>
            <div class="dropdown-menu-list" v-show="collapseSampleSelectionDropdownMenu">
              <div style="position: relative; overflow: hidden; width: 100%; height: auto; min-height: 0px; max-height: 100%;">
                <div class="scrollbar-view" style="position: relative; overflow: scroll; margin-right: -8px; margin-bottom: -8px; max-height: calc(100% + 8px);">
                  <ul style="list-style-type:none; padding-left: 0px; margin-top: 5px; margin-bottom: 5px;">
                    <li v-for="option in sampleDropdownMenuOptions" :key="option.label" class="dropdown-menu-list-item">
                      <el-button @click="filterLastNSamples(option)" class="dropdown-menu-list-item-button">{{ option.label }}</el-button>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>

          <div style="position: relative;">
            <el-button size="small" class="toolbar-button" style="padding: 2px 8px;" @click.stop="collapseLandmarkSelectionDropdownMenu = !collapseLandmarkSelectionDropdownMenu">
              <div style="display: flex; align-items: center;">
                <div>
                  <svg t="1693320827578" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="6865" width="16" height="16">
                    <path d="M688.896 0v1024l261.717333-208.469333V142.250667z m-121.728 800.341333l-210.730667 92.032V89.002667l210.730667 62.378666z m-493.738667-23.594666l161.28-42.624V221.354667l-160.512-25.856z" p-id="6866"></path>
                  </svg>
                </div>
                <div style="padding-left:8px; padding-right:4px;">
                  <span style="font-size: 13px; font-weight: 600; color:rgba(36, 41, 46, 0.75)">
                    <span>{{ selectedLandmark }}</span>
                  </span>
                </div>
                <div>
                  <svg v-show="!collapseLandmarkSelectionDropdownMenu" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16">
                    <path d="M17,9.17a1,1,0,0,0-1.41,0L12,12.71,8.46,9.17a1,1,0,0,0-1.41,0,1,1,0,0,0,0,1.42l4.24,4.24a1,1,0,0,0,1.42,0L17,10.59A1,1,0,0,0,17,9.17Z"></path>
                  </svg>
                  <svg v-show="collapseLandmarkSelectionDropdownMenu" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-ovd6k">
                    <path d="M17,13.41,12.71,9.17a1,1,0,0,0-1.42,0L7.05,13.41a1,1,0,0,0,0,1.42,1,1,0,0,0,1.41,0L12,11.29l3.54,3.54a1,1,0,0,0,.7.29,1,1,0,0,0,.71-.29A1,1,0,0,0,17,13.41Z"></path>
                  </svg>
                </div>
              </div>
            </el-button>
            <div class="dropdown-menu-list" v-show="collapseLandmarkSelectionDropdownMenu">
              <div style="position: relative; overflow: hidden; width: 100%; height: auto; min-height: 0px; max-height: 100%;">
                <div class="scrollbar-view" style="position: relative; overflow: scroll; margin-right: -8px; margin-bottom: -8px; max-height: calc(100% + 8px);">
                  <ul style="list-style-type:none; padding-left: 0px; margin-top: 5px; margin-bottom: 5px;">
                    <li v-for="option in [{'id':'0000', name:'All landmarks'}].concat(landmarkData)" :key="option.id" class="dropdown-menu-list-item">
                      <el-button @click="filterLandmarks(option.name)" class="dropdown-menu-list-item-button">{{ option.name }}</el-button>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>

          <el-button size="small" @click="start" :disabled="monitorTask.monitoring" class="primary-button" style="margin-left:0px; display:flex; align-items:center;">Start</el-button>
          <el-button size="small" @click="stop" :disabled="!monitorTask.monitoring" class="danger-button" style="margin-left: 0px;">Stop</el-button>
          <el-tooltip class="item" effect="dark" content="Cycle view mode" placement="bottom">
            <el-button size="mini" class="toolbar-button" @click="toggleViewMode" style="margin-left: 0px;">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" style="fill:currentColor">
                <path d="M19,2H5A3,3,0,0,0,2,5V15a3,3,0,0,0,3,3H7.64l-.58,1a2,2,0,0,0,0,2,2,2,0,0,0,1.75,1h6.46A2,2,0,0,0,17,21a2,2,0,0,0,0-2l-.59-1H19a3,3,0,0,0,3-3V5A3,3,0,0,0,19,2ZM8.77,20,10,18H14l1.2,2ZM20,15a1,1,0,0,1-1,1H5a1,1,0,0,1-1-1V14H20Zm0-3H4V5A1,1,0,0,1,5,4H19a1,1,0,0,1,1,1Z"></path>
              </svg>
            </el-button>
          </el-tooltip>
        </div>
      </div>
      <div id="main" style="background-color: #f4f5f5;" :style="{ width: layout.main.width + 'px', height: layout.main.height + 'px' }" v-if="router.path === 'overview'">
        <grid-layout :layout.sync="gridLayout" :col-num="48" :row-height="gridRowHeight" :is-draggable="true" :is-resizable="true" :is-mirrored="false"
          :vertical-compact="true" :margin="[10, 10]" :use-css-transforms="true">
          <grid-item v-for="item in gridLayout" :x="item.x" :y="item.y" :w="item.w" :h="item.h" :i="item.i" :key="item.i" @resize="resizePanel" drag-allow-from=".vue-draggable-handle" drag-ignore-from=".no-drag">
            <div v-if="item.i === 0">
              <div class="panelToolsBar vue-draggable-handle">
                <el-dropdown trigger="click" @command="handleDropdownOfPanel">
                  <span>{{ currentChromatogram }}<i class="el-icon-arrow-down el-icon--right"></i></span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="viewIonCurrent">
                      <div style="display: flex; align-items: center;">
                        <div style="padding-top:4px">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M21.92,11.6C19.9,6.91,16.1,4,12,4S4.1,6.91,2.08,11.6a1,1,0,0,0,0,.8C4.1,17.09,7.9,20,12,20s7.9-2.91,9.92-7.6A1,1,0,0,0,21.92,11.6ZM12,18c-3.17,0-6.17-2.29-7.9-6C5.83,8.29,8.83,6,12,6s6.17,2.29,7.9,6C18.17,15.71,15.17,18,12,18ZM12,8a4,4,0,1,0,4,4A4,4,0,0,0,12,8Zm0,6a2,2,0,1,1,2-2A2,2,0,0,1,12,14Z"></path></svg>
                        </div>
                        <span style="padding-left:5px; padding-right: 20px;">View</span>
                      </div>
                    </el-dropdown-item>
                    <!--
                    <el-dropdown-item command="editIonCurrent">
                      <div style="display: flex; align-items: center;">
                        <div style="padding-top:4px">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M21,12a1,1,0,0,0-1,1v6a1,1,0,0,1-1,1H5a1,1,0,0,1-1-1V5A1,1,0,0,1,5,4h6a1,1,0,0,0,0-2H5A3,3,0,0,0,2,5V19a3,3,0,0,0,3,3H19a3,3,0,0,0,3-3V13A1,1,0,0,0,21,12ZM6,12.76V17a1,1,0,0,0,1,1h4.24a1,1,0,0,0,.71-.29l6.92-6.93h0L21.71,8a1,1,0,0,0,0-1.42L17.47,2.29a1,1,0,0,0-1.42,0L13.23,5.12h0L6.29,12.05A1,1,0,0,0,6,12.76ZM16.76,4.41l2.83,2.83L18.17,8.66,15.34,5.83ZM8,13.17l5.93-5.93,2.83,2.83L10.83,16H8Z"></path></svg>
                        </div>
                        <span style="padding-left:5px; padding-right: 20px;">Edit</span>
                      </div>
                    </el-dropdown-item>
                    -->
                    <el-dropdown-item command="switchIonCurrent1">
                      <div style="display: flex; align-items: center;">
                        <div style="padding-top:4px">
                          <svg t="1687500158194" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2803" width="14" height="14"><path d="M737.718857 292.571429L637.805714 192.658286a73.142857 73.142857 0 1 1 103.424-103.424l229.229715 229.229714a73.142857 73.142857 0 0 1-79.798858 119.222857C886.491429 438.418286 882.102857 438.857143 877.714286 438.857143H73.142857a73.142857 73.142857 0 1 1 0-146.285714h664.576zM254.171429 731.428571l99.913142 99.913143a73.142857 73.142857 0 1 1-103.424 103.424L21.430857 705.536a73.142857 73.142857 0 0 1 79.725714-119.222857C105.472 585.581714 109.714286 585.142857 114.249143 585.142857h804.571428a73.142857 73.142857 0 1 1 0 146.285714H254.171429z" fill="#747A7D" p-id="2804"></path></svg>
                        </div>
                        <span style="margin-left:5px;">{{ switchChromatogram1 }}</span>
                      </div>
                    </el-dropdown-item>
                    <el-dropdown-item command="switchIonCurrent2">
                      <div style="display: flex; align-items: center;">
                        <div style="padding-top:4px">
                          <svg t="1687500158194" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2803" width="14" height="14"><path d="M737.718857 292.571429L637.805714 192.658286a73.142857 73.142857 0 1 1 103.424-103.424l229.229715 229.229714a73.142857 73.142857 0 0 1-79.798858 119.222857C886.491429 438.418286 882.102857 438.857143 877.714286 438.857143H73.142857a73.142857 73.142857 0 1 1 0-146.285714h664.576zM254.171429 731.428571l99.913142 99.913143a73.142857 73.142857 0 1 1-103.424 103.424L21.430857 705.536a73.142857 73.142857 0 0 1 79.725714-119.222857C105.472 585.581714 109.714286 585.142857 114.249143 585.142857h804.571428a73.142857 73.142857 0 1 1 0 146.285714H254.171429z" fill="#747A7D" p-id="2804"></path></svg>
                        </div>
                        <span style="margin-left:5px;">{{ switchChromatogram2 }}</span>
                      </div>
                    </el-dropdown-item>
                    <el-dropdown-item style="padding: 0px 0px 0px 10px">
                      <el-dropdown placement="right-start" style="width:100%" @command="filterIonCurrentData">
                        <div style="display:flex; justify-content: space-between;">
                          <div style="display:flex; align-items:center; width:200px">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M20.47,7.37s0,0,0-.08l-.06-.15a.71.71,0,0,0-.07-.09.94.94,0,0,0-.09-.12l-.09-.07L20,6.78l-7.5-4.63a1,1,0,0,0-1.06,0L4,6.78l-.09.08-.09.07a.94.94,0,0,0-.09.12.71.71,0,0,0-.07.09l-.06.15s0,0,0,.08a1.15,1.15,0,0,0,0,.26v8.74a1,1,0,0,0,.47.85l7.5,4.63h0a.47.47,0,0,0,.15.06s.05,0,.08,0a.86.86,0,0,0,.52,0s.05,0,.08,0a.47.47,0,0,0,.15-.06h0L20,17.22a1,1,0,0,0,.47-.85V7.63A1.15,1.15,0,0,0,20.47,7.37ZM11,19.21l-5.5-3.4V9.43L11,12.82Zm1-8.12L6.4,7.63,12,4.18l5.6,3.45Zm6.5,4.72L13,19.21V12.82l5.5-3.39Z"></path></svg>
                            <span style="margin-left:3px">Select Data</span>
                          </div>
                          <div style="display:flex; align-items:center;">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-ejzmsl"><path d="M14.83,11.29,10.59,7.05a1,1,0,0,0-1.42,0,1,1,0,0,0,0,1.41L12.71,12,9.17,15.54a1,1,0,0,0,0,1.41,1,1,0,0,0,.71.29,1,1,0,0,0,.71-.29l4.24-4.24A1,1,0,0,0,14.83,11.29Z"></path></svg>
                          </div>
                        </div>
                        <template v-if="currentChromatogram.startsWith('Extract')">
                          <el-dropdown-menu slot="dropdown" style="max-height: 500px; overflow-y: scroll;">
                            <el-dropdown-item v-for="option in [{'id':'0000', name:'All data'}].concat(landmarkData)" :key="option.id" :command=option.name style="font-size:12px;">{{ option.name }}</el-dropdown-item>
                          </el-dropdown-menu>
                        </template>
                        <template v-else>
                          <el-dropdown-menu slot="dropdown" style="max-height: 500px; overflow-y: scroll;">
                            <el-dropdown-item v-for="option in ['All data'].concat(ionCurrentDataFilterOptions)" :key="option" :command=option style="font-size:12px;">{{ option }}</el-dropdown-item>
                          </el-dropdown-menu>
                        </template>
                      </el-dropdown>
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
              <div class="no-drag">
                <vue-plotly :data="ionCurrentData" :layout="ionCurrentLayout" :options="defaultPlotOptions" style="height: calc(100% - 40px);"></vue-plotly>
              </div>
            </div>
            <div v-if="item.i === 1">
              <div class="panelToolsBar vue-draggable-handle">
                <el-dropdown trigger="click" @command="handleDropdownOfPanel">
                  <span>{{ panelTitleOfRT }}<i class="el-icon-arrow-down el-icon--right"></i></span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="viewLandmarkRt">
                      <div style="display: flex; align-items: center;">
                        <div style="padding-top:4px">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M21.92,11.6C19.9,6.91,16.1,4,12,4S4.1,6.91,2.08,11.6a1,1,0,0,0,0,.8C4.1,17.09,7.9,20,12,20s7.9-2.91,9.92-7.6A1,1,0,0,0,21.92,11.6ZM12,18c-3.17,0-6.17-2.29-7.9-6C5.83,8.29,8.83,6,12,6s6.17,2.29,7.9,6C18.17,15.71,15.17,18,12,18ZM12,8a4,4,0,1,0,4,4A4,4,0,0,0,12,8Zm0,6a2,2,0,1,1,2-2A2,2,0,0,1,12,14Z"></path></svg>
                        </div>
                        <span style="padding-left:5px; padding-right: 20px;">View</span>
                      </div>
                    </el-dropdown-item>
                    <el-dropdown-item command="editLandmarkRt">
                      <div style="display: flex; align-items: center;">
                        <div style="padding-top:4px">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M21,12a1,1,0,0,0-1,1v6a1,1,0,0,1-1,1H5a1,1,0,0,1-1-1V5A1,1,0,0,1,5,4h6a1,1,0,0,0,0-2H5A3,3,0,0,0,2,5V19a3,3,0,0,0,3,3H19a3,3,0,0,0,3-3V13A1,1,0,0,0,21,12ZM6,12.76V17a1,1,0,0,0,1,1h4.24a1,1,0,0,0,.71-.29l6.92-6.93h0L21.71,8a1,1,0,0,0,0-1.42L17.47,2.29a1,1,0,0,0-1.42,0L13.23,5.12h0L6.29,12.05A1,1,0,0,0,6,12.76ZM16.76,4.41l2.83,2.83L18.17,8.66,15.34,5.83ZM8,13.17l5.93-5.93,2.83,2.83L10.83,16H8Z"></path></svg>
                        </div>
                        <span style="padding-left:5px; padding-right: 20px;">Edit</span>
                      </div>
                    </el-dropdown-item>
                    <el-dropdown-item style="padding: 0px 0px 0px 10px">
                      <el-dropdown placement="right-start" style="width:100%" @command="filterLandmarkRtData">
                        <div style="display:flex; justify-content: space-between;">
                          <div style="display:flex; align-items:center; width:120px">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M20.47,7.37s0,0,0-.08l-.06-.15a.71.71,0,0,0-.07-.09.94.94,0,0,0-.09-.12l-.09-.07L20,6.78l-7.5-4.63a1,1,0,0,0-1.06,0L4,6.78l-.09.08-.09.07a.94.94,0,0,0-.09.12.71.71,0,0,0-.07.09l-.06.15s0,0,0,.08a1.15,1.15,0,0,0,0,.26v8.74a1,1,0,0,0,.47.85l7.5,4.63h0a.47.47,0,0,0,.15.06s.05,0,.08,0a.86.86,0,0,0,.52,0s.05,0,.08,0a.47.47,0,0,0,.15-.06h0L20,17.22a1,1,0,0,0,.47-.85V7.63A1.15,1.15,0,0,0,20.47,7.37ZM11,19.21l-5.5-3.4V9.43L11,12.82Zm1-8.12L6.4,7.63,12,4.18l5.6,3.45Zm6.5,4.72L13,19.21V12.82l5.5-3.39Z"></path></svg>
                            <span style="margin-left:3px">Select Data</span>
                          </div>
                          <div style="display:flex; align-items:center;">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-ejzmsl"><path d="M14.83,11.29,10.59,7.05a1,1,0,0,0-1.42,0,1,1,0,0,0,0,1.41L12.71,12,9.17,15.54a1,1,0,0,0,0,1.41,1,1,0,0,0,.71.29,1,1,0,0,0,.71-.29l4.24-4.24A1,1,0,0,0,14.83,11.29Z"></path></svg>
                          </div>
                        </div>
                        <el-dropdown-menu slot="dropdown">
                          <el-dropdown-item v-for="option in [{'id':'0000', name:'All data'}].concat(landmarkData)" :key="option.id" :command=option.name style="font-size:12px;">{{ option.name }}</el-dropdown-item>
                        </el-dropdown-menu>
                      </el-dropdown>
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
              <div class="no-drag">
                <vue-plotly :data="selectedLandmarkRtData" :layout="landmarkRtLayout" :options="defaultPlotOptions" style="height: calc(100% - 40px);"></vue-plotly>
              </div>
            </div>
            <div v-if="item.i === 2">
              <div class="panelToolsBar vue-draggable-handle">
                <el-dropdown trigger="click" @command="handleDropdownOfPanel">
                  <span>{{ panelTitleOfIntensity }}<i class="el-icon-arrow-down el-icon--right"></i></span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="viewLandmarkIntensity">
                      <div style="display: flex; align-items: center;">
                        <div style="padding-top:4px">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M21.92,11.6C19.9,6.91,16.1,4,12,4S4.1,6.91,2.08,11.6a1,1,0,0,0,0,.8C4.1,17.09,7.9,20,12,20s7.9-2.91,9.92-7.6A1,1,0,0,0,21.92,11.6ZM12,18c-3.17,0-6.17-2.29-7.9-6C5.83,8.29,8.83,6,12,6s6.17,2.29,7.9,6C18.17,15.71,15.17,18,12,18ZM12,8a4,4,0,1,0,4,4A4,4,0,0,0,12,8Zm0,6a2,2,0,1,1,2-2A2,2,0,0,1,12,14Z"></path></svg>
                        </div>
                        <span style="padding-left:5px; padding-right: 20px;">View</span>
                      </div>
                    </el-dropdown-item>
                    <el-dropdown-item command="editLandmarkIntensity">
                      <div style="display: flex; align-items: center;">
                        <div style="padding-top:4px">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M21,12a1,1,0,0,0-1,1v6a1,1,0,0,1-1,1H5a1,1,0,0,1-1-1V5A1,1,0,0,1,5,4h6a1,1,0,0,0,0-2H5A3,3,0,0,0,2,5V19a3,3,0,0,0,3,3H19a3,3,0,0,0,3-3V13A1,1,0,0,0,21,12ZM6,12.76V17a1,1,0,0,0,1,1h4.24a1,1,0,0,0,.71-.29l6.92-6.93h0L21.71,8a1,1,0,0,0,0-1.42L17.47,2.29a1,1,0,0,0-1.42,0L13.23,5.12h0L6.29,12.05A1,1,0,0,0,6,12.76ZM16.76,4.41l2.83,2.83L18.17,8.66,15.34,5.83ZM8,13.17l5.93-5.93,2.83,2.83L10.83,16H8Z"></path></svg>
                        </div>
                        <span style="padding-left:5px; padding-right: 20px;">Edit</span>
                      </div>
                    </el-dropdown-item>
                    <el-dropdown-item style="padding: 0px 0px 0px 10px">
                      <el-dropdown placement="right-start" style="width:100%" @command="filterLandmarkIntensityData">
                        <div style="display:flex; justify-content: space-between;">
                          <div style="display:flex; align-items:center; width:120px">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M20.47,7.37s0,0,0-.08l-.06-.15a.71.71,0,0,0-.07-.09.94.94,0,0,0-.09-.12l-.09-.07L20,6.78l-7.5-4.63a1,1,0,0,0-1.06,0L4,6.78l-.09.08-.09.07a.94.94,0,0,0-.09.12.71.71,0,0,0-.07.09l-.06.15s0,0,0,.08a1.15,1.15,0,0,0,0,.26v8.74a1,1,0,0,0,.47.85l7.5,4.63h0a.47.47,0,0,0,.15.06s.05,0,.08,0a.86.86,0,0,0,.52,0s.05,0,.08,0a.47.47,0,0,0,.15-.06h0L20,17.22a1,1,0,0,0,.47-.85V7.63A1.15,1.15,0,0,0,20.47,7.37ZM11,19.21l-5.5-3.4V9.43L11,12.82Zm1-8.12L6.4,7.63,12,4.18l5.6,3.45Zm6.5,4.72L13,19.21V12.82l5.5-3.39Z"></path></svg>
                            <span style="margin-left:3px">Select Data</span>
                          </div>
                          <div style="display:flex; align-items:center;">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-ejzmsl"><path d="M14.83,11.29,10.59,7.05a1,1,0,0,0-1.42,0,1,1,0,0,0,0,1.41L12.71,12,9.17,15.54a1,1,0,0,0,0,1.41,1,1,0,0,0,.71.29,1,1,0,0,0,.71-.29l4.24-4.24A1,1,0,0,0,14.83,11.29Z"></path></svg>
                          </div>
                        </div>
                        <el-dropdown-menu slot="dropdown">
                          <el-dropdown-item v-for="option in [{'id':'0000', name:'All data'}].concat(landmarkData)" :key="option.id" :command=option.name style="font-size:12px;">{{ option.name }}</el-dropdown-item>
                        </el-dropdown-menu>
                      </el-dropdown>
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
              <div class="no-drag">
                <vue-plotly :data="selectedLandmarkIntensityData" :layout="landmarkIntensityLayout" :options="defaultPlotOptions" style="height: calc(100% - 40px);"></vue-plotly>
              </div>
            </div>
            <div v-if="item.i === 3">
              <div class="panelToolsBar vue-draggable-handle">
                <el-dropdown trigger="click" @command="handleDropdownOfPanel">
                  <span>{{ panelTitleOfRelMzError }}<i class="el-icon-arrow-down el-icon--right"></i></span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="viewLandmarkRelMzError">
                      <div style="display: flex; align-items: center;">
                        <div style="padding-top:4px">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M21.92,11.6C19.9,6.91,16.1,4,12,4S4.1,6.91,2.08,11.6a1,1,0,0,0,0,.8C4.1,17.09,7.9,20,12,20s7.9-2.91,9.92-7.6A1,1,0,0,0,21.92,11.6ZM12,18c-3.17,0-6.17-2.29-7.9-6C5.83,8.29,8.83,6,12,6s6.17,2.29,7.9,6C18.17,15.71,15.17,18,12,18ZM12,8a4,4,0,1,0,4,4A4,4,0,0,0,12,8Zm0,6a2,2,0,1,1,2-2A2,2,0,0,1,12,14Z"></path></svg>
                        </div>
                        <span style="padding-left:5px; padding-right: 20px;">View</span>
                      </div>
                    </el-dropdown-item>
                    <el-dropdown-item command="editLandmarkRelMzError">
                      <div style="display: flex; align-items: center;">
                        <div style="padding-top:4px">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M21,12a1,1,0,0,0-1,1v6a1,1,0,0,1-1,1H5a1,1,0,0,1-1-1V5A1,1,0,0,1,5,4h6a1,1,0,0,0,0-2H5A3,3,0,0,0,2,5V19a3,3,0,0,0,3,3H19a3,3,0,0,0,3-3V13A1,1,0,0,0,21,12ZM6,12.76V17a1,1,0,0,0,1,1h4.24a1,1,0,0,0,.71-.29l6.92-6.93h0L21.71,8a1,1,0,0,0,0-1.42L17.47,2.29a1,1,0,0,0-1.42,0L13.23,5.12h0L6.29,12.05A1,1,0,0,0,6,12.76ZM16.76,4.41l2.83,2.83L18.17,8.66,15.34,5.83ZM8,13.17l5.93-5.93,2.83,2.83L10.83,16H8Z"></path></svg>
                        </div>
                        <span style="padding-left:5px; padding-right: 20px;">Edit</span>
                      </div>
                    </el-dropdown-item>
                    <el-dropdown-item style="padding: 0px 0px 0px 10px">
                      <el-dropdown placement="right-start" style="width:100%" @command="filterLandmarkRelMzErrorData">
                        <div style="display:flex; justify-content: space-between;">
                          <div style="display:flex; align-items:center; width:120px">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M20.47,7.37s0,0,0-.08l-.06-.15a.71.71,0,0,0-.07-.09.94.94,0,0,0-.09-.12l-.09-.07L20,6.78l-7.5-4.63a1,1,0,0,0-1.06,0L4,6.78l-.09.08-.09.07a.94.94,0,0,0-.09.12.71.71,0,0,0-.07.09l-.06.15s0,0,0,.08a1.15,1.15,0,0,0,0,.26v8.74a1,1,0,0,0,.47.85l7.5,4.63h0a.47.47,0,0,0,.15.06s.05,0,.08,0a.86.86,0,0,0,.52,0s.05,0,.08,0a.47.47,0,0,0,.15-.06h0L20,17.22a1,1,0,0,0,.47-.85V7.63A1.15,1.15,0,0,0,20.47,7.37ZM11,19.21l-5.5-3.4V9.43L11,12.82Zm1-8.12L6.4,7.63,12,4.18l5.6,3.45Zm6.5,4.72L13,19.21V12.82l5.5-3.39Z"></path></svg>
                            <span style="margin-left:3px">Select Data</span>
                          </div>
                          <div style="display:flex; align-items:center;">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-ejzmsl"><path d="M14.83,11.29,10.59,7.05a1,1,0,0,0-1.42,0,1,1,0,0,0,0,1.41L12.71,12,9.17,15.54a1,1,0,0,0,0,1.41,1,1,0,0,0,.71.29,1,1,0,0,0,.71-.29l4.24-4.24A1,1,0,0,0,14.83,11.29Z"></path></svg>
                          </div>
                        </div>
                        <el-dropdown-menu slot="dropdown">
                          <el-dropdown-item v-for="option in [{'id':'0000', name:'All data'}].concat(landmarkData)" :key="option.id" :command=option.name style="font-size:12px;">{{ option.name }}</el-dropdown-item>
                        </el-dropdown-menu>
                      </el-dropdown>
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
              <div class="no-drag">
                <vue-plotly :data="selectedLandmarkRelMzErrorData" :layout="landmarkRelMzErrorLayout" :options="defaultPlotOptions" style="height: calc(100% - 40px);"></vue-plotly>
              </div>
            </div>
            <div v-if="item.i === 4">
              <div class="panelToolsBar vue-draggable-handle">
                <el-dropdown trigger="click" @command="handleDropdownOfPanel">
                  <span>{{ panelTitleOfAbsMzError }}<i class="el-icon-arrow-down el-icon--right"></i></span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="viewLandmarkAbsMzError">
                      <div style="display: flex; align-items: center;">
                        <div style="padding-top:4px">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M21.92,11.6C19.9,6.91,16.1,4,12,4S4.1,6.91,2.08,11.6a1,1,0,0,0,0,.8C4.1,17.09,7.9,20,12,20s7.9-2.91,9.92-7.6A1,1,0,0,0,21.92,11.6ZM12,18c-3.17,0-6.17-2.29-7.9-6C5.83,8.29,8.83,6,12,6s6.17,2.29,7.9,6C18.17,15.71,15.17,18,12,18ZM12,8a4,4,0,1,0,4,4A4,4,0,0,0,12,8Zm0,6a2,2,0,1,1,2-2A2,2,0,0,1,12,14Z"></path></svg>
                        </div>
                        <span style="padding-left:5px; padding-right: 20px;">View</span>
                      </div>
                    </el-dropdown-item>
                    <el-dropdown-item command="editLandmarkAbsMzError">
                      <div style="display: flex; align-items: center;">
                        <div style="padding-top:4px">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M21,12a1,1,0,0,0-1,1v6a1,1,0,0,1-1,1H5a1,1,0,0,1-1-1V5A1,1,0,0,1,5,4h6a1,1,0,0,0,0-2H5A3,3,0,0,0,2,5V19a3,3,0,0,0,3,3H19a3,3,0,0,0,3-3V13A1,1,0,0,0,21,12ZM6,12.76V17a1,1,0,0,0,1,1h4.24a1,1,0,0,0,.71-.29l6.92-6.93h0L21.71,8a1,1,0,0,0,0-1.42L17.47,2.29a1,1,0,0,0-1.42,0L13.23,5.12h0L6.29,12.05A1,1,0,0,0,6,12.76ZM16.76,4.41l2.83,2.83L18.17,8.66,15.34,5.83ZM8,13.17l5.93-5.93,2.83,2.83L10.83,16H8Z"></path></svg>
                        </div>
                        <span style="padding-left:5px; padding-right: 20px;">Edit</span>
                      </div>
                    </el-dropdown-item>
                    <el-dropdown-item style="padding: 0px 0px 0px 10px">
                      <el-dropdown placement="right-start" style="width:100%" @command="filterLandmarkAbsMzErrorData">
                        <div style="display:flex; justify-content: space-between;">
                          <div style="display:flex; align-items:center; width:120px">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M20.47,7.37s0,0,0-.08l-.06-.15a.71.71,0,0,0-.07-.09.94.94,0,0,0-.09-.12l-.09-.07L20,6.78l-7.5-4.63a1,1,0,0,0-1.06,0L4,6.78l-.09.08-.09.07a.94.94,0,0,0-.09.12.71.71,0,0,0-.07.09l-.06.15s0,0,0,.08a1.15,1.15,0,0,0,0,.26v8.74a1,1,0,0,0,.47.85l7.5,4.63h0a.47.47,0,0,0,.15.06s.05,0,.08,0a.86.86,0,0,0,.52,0s.05,0,.08,0a.47.47,0,0,0,.15-.06h0L20,17.22a1,1,0,0,0,.47-.85V7.63A1.15,1.15,0,0,0,20.47,7.37ZM11,19.21l-5.5-3.4V9.43L11,12.82Zm1-8.12L6.4,7.63,12,4.18l5.6,3.45Zm6.5,4.72L13,19.21V12.82l5.5-3.39Z"></path></svg>
                            <span style="margin-left:3px">Select Data</span>
                          </div>
                          <div style="display:flex; align-items:center;">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-ejzmsl"><path d="M14.83,11.29,10.59,7.05a1,1,0,0,0-1.42,0,1,1,0,0,0,0,1.41L12.71,12,9.17,15.54a1,1,0,0,0,0,1.41,1,1,0,0,0,.71.29,1,1,0,0,0,.71-.29l4.24-4.24A1,1,0,0,0,14.83,11.29Z"></path></svg>
                          </div>
                        </div>
                        <el-dropdown-menu slot="dropdown">
                          <el-dropdown-item v-for="option in [{'id':'0000', name:'All data'}].concat(landmarkData)" :key="option.id" :command=option.name style="font-size:12px;">{{ option.name }}</el-dropdown-item>
                        </el-dropdown-menu>
                      </el-dropdown>
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
              <div class="no-drag">
                <vue-plotly :data="selectedLandmarkAbsMzErrorData" :layout="landmarkAbsMzErrorLayout" :options="defaultPlotOptions" style="height: calc(100% - 40px);"></vue-plotly>
              </div>
            </div>
            <div v-if="item.i === 5">
              <div class="panelToolsBar vue-draggable-handle">
                <el-dropdown trigger="click" @command="handleDropdownOfPanel">
                  <span>{{ panelTitleOfPeakArea }}<i class="el-icon-arrow-down el-icon--right"></i></span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="viewLandmarkPeakArea">
                      <div style="display: flex; align-items: center;">
                        <div style="padding-top:4px">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M21.92,11.6C19.9,6.91,16.1,4,12,4S4.1,6.91,2.08,11.6a1,1,0,0,0,0,.8C4.1,17.09,7.9,20,12,20s7.9-2.91,9.92-7.6A1,1,0,0,0,21.92,11.6ZM12,18c-3.17,0-6.17-2.29-7.9-6C5.83,8.29,8.83,6,12,6s6.17,2.29,7.9,6C18.17,15.71,15.17,18,12,18ZM12,8a4,4,0,1,0,4,4A4,4,0,0,0,12,8Zm0,6a2,2,0,1,1,2-2A2,2,0,0,1,12,14Z"></path></svg>
                        </div>
                        <span style="padding-left:5px; padding-right: 20px;">View</span>
                      </div>
                    </el-dropdown-item>
                    <el-dropdown-item command="editLandmarkPeakArea">
                      <div style="display: flex; align-items: center;">
                        <div style="padding-top:4px">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M21,12a1,1,0,0,0-1,1v6a1,1,0,0,1-1,1H5a1,1,0,0,1-1-1V5A1,1,0,0,1,5,4h6a1,1,0,0,0,0-2H5A3,3,0,0,0,2,5V19a3,3,0,0,0,3,3H19a3,3,0,0,0,3-3V13A1,1,0,0,0,21,12ZM6,12.76V17a1,1,0,0,0,1,1h4.24a1,1,0,0,0,.71-.29l6.92-6.93h0L21.71,8a1,1,0,0,0,0-1.42L17.47,2.29a1,1,0,0,0-1.42,0L13.23,5.12h0L6.29,12.05A1,1,0,0,0,6,12.76ZM16.76,4.41l2.83,2.83L18.17,8.66,15.34,5.83ZM8,13.17l5.93-5.93,2.83,2.83L10.83,16H8Z"></path></svg>
                        </div>
                        <span style="padding-left:5px; padding-right: 20px;">Edit</span>
                      </div>
                    </el-dropdown-item>
                    <el-dropdown-item style="padding: 0px 0px 0px 10px">
                      <el-dropdown placement="right-start" style="width:100%" @command="filterLandmarkPeakAreaData">
                        <div style="display:flex; justify-content: space-between;">
                          <div style="display:flex; align-items:center; width:120px">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M20.47,7.37s0,0,0-.08l-.06-.15a.71.71,0,0,0-.07-.09.94.94,0,0,0-.09-.12l-.09-.07L20,6.78l-7.5-4.63a1,1,0,0,0-1.06,0L4,6.78l-.09.08-.09.07a.94.94,0,0,0-.09.12.71.71,0,0,0-.07.09l-.06.15s0,0,0,.08a1.15,1.15,0,0,0,0,.26v8.74a1,1,0,0,0,.47.85l7.5,4.63h0a.47.47,0,0,0,.15.06s.05,0,.08,0a.86.86,0,0,0,.52,0s.05,0,.08,0a.47.47,0,0,0,.15-.06h0L20,17.22a1,1,0,0,0,.47-.85V7.63A1.15,1.15,0,0,0,20.47,7.37ZM11,19.21l-5.5-3.4V9.43L11,12.82Zm1-8.12L6.4,7.63,12,4.18l5.6,3.45Zm6.5,4.72L13,19.21V12.82l5.5-3.39Z"></path></svg>
                            <span style="margin-left:3px">Select Data</span>
                          </div>
                          <div style="display:flex; align-items:center;">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-ejzmsl"><path d="M14.83,11.29,10.59,7.05a1,1,0,0,0-1.42,0,1,1,0,0,0,0,1.41L12.71,12,9.17,15.54a1,1,0,0,0,0,1.41,1,1,0,0,0,.71.29,1,1,0,0,0,.71-.29l4.24-4.24A1,1,0,0,0,14.83,11.29Z"></path></svg>
                          </div>
                        </div>
                        <el-dropdown-menu slot="dropdown">
                          <el-dropdown-item v-for="option in [{'id':'0000', name:'All data'}].concat(landmarkData)" :key="option.id" :command=option.name style="font-size:12px;">{{ option.name }}</el-dropdown-item>
                        </el-dropdown-menu>
                      </el-dropdown>
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
              <div class="no-drag">
                <vue-plotly :data="selectedLandmarkPeakAreaData" :layout="landmarkPeakAreaLayout" :options="defaultPlotOptions" style="height: calc(100% - 40px);"></vue-plotly>
              </div>
            </div>
          </grid-item>
        </grid-layout>
      </div>

      <div style="background-color: #f4f5f5;" v-if="router.path === 'detail'">
        <el-button size="mini" @click="goBack" round style="margin:0px 10px" class="back-button">
          <svg xmlns="http://www.w3.org/2000/svg" data-name="Layer 1" viewBox="0 0 24 24" width="30" height="30"><path d="M17,11H9.41l3.3-3.29a1,1,0,1,0-1.42-1.42l-5,5a1,1,0,0,0-.21.33,1,1,0,0,0,0,.76,1,1,0,0,0,.21.33l5,5a1,1,0,0,0,1.42,0,1,1,0,0,0,0-1.42L9.41,13H17a1,1,0,0,0,0-2Z"></path></svg>
        </el-button>
        <div style="margin:10px;">
          <div class="panelToolsBar">
            <el-dropdown trigger="click" @command="handleDropdownOfDetailPanel">
              <span>{{ detailPanelTitle }}<i class="el-icon-arrow-down el-icon--right"></i></span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item :command=editDetailPanelCommand>
                  <div style="display: flex; align-items: center;">
                    <div style="padding-top:4px">
                      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M21,12a1,1,0,0,0-1,1v6a1,1,0,0,1-1,1H5a1,1,0,0,1-1-1V5A1,1,0,0,1,5,4h6a1,1,0,0,0,0-2H5A3,3,0,0,0,2,5V19a3,3,0,0,0,3,3H19a3,3,0,0,0,3-3V13A1,1,0,0,0,21,12ZM6,12.76V17a1,1,0,0,0,1,1h4.24a1,1,0,0,0,.71-.29l6.92-6.93h0L21.71,8a1,1,0,0,0,0-1.42L17.47,2.29a1,1,0,0,0-1.42,0L13.23,5.12h0L6.29,12.05A1,1,0,0,0,6,12.76ZM16.76,4.41l2.83,2.83L18.17,8.66,15.34,5.83ZM8,13.17l5.93-5.93,2.83,2.83L10.83,16H8Z"></path></svg>
                    </div>
                    <span style="padding-left:5px; padding-right: 20px;">Edit</span>
                  </div>
                </el-dropdown-item>
                <el-dropdown-item style="padding: 0px 0px 0px 10px">
                  <el-dropdown placement="right-start" style="width:100%" @command="handleFilterDetailPanelData">
                    <div style="display:flex; justify-content: space-between;">
                      <div style="display:flex; align-items:center; width:120px">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M20.47,7.37s0,0,0-.08l-.06-.15a.71.71,0,0,0-.07-.09.94.94,0,0,0-.09-.12l-.09-.07L20,6.78l-7.5-4.63a1,1,0,0,0-1.06,0L4,6.78l-.09.08-.09.07a.94.94,0,0,0-.09.12.71.71,0,0,0-.07.09l-.06.15s0,0,0,.08a1.15,1.15,0,0,0,0,.26v8.74a1,1,0,0,0,.47.85l7.5,4.63h0a.47.47,0,0,0,.15.06s.05,0,.08,0a.86.86,0,0,0,.52,0s.05,0,.08,0a.47.47,0,0,0,.15-.06h0L20,17.22a1,1,0,0,0,.47-.85V7.63A1.15,1.15,0,0,0,20.47,7.37ZM11,19.21l-5.5-3.4V9.43L11,12.82Zm1-8.12L6.4,7.63,12,4.18l5.6,3.45Zm6.5,4.72L13,19.21V12.82l5.5-3.39Z"></path></svg>
                        <span style="margin-left:3px">Select Data</span>
                      </div>
                      <div style="display:flex; align-items:center;">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-ejzmsl"><path d="M14.83,11.29,10.59,7.05a1,1,0,0,0-1.42,0,1,1,0,0,0,0,1.41L12.71,12,9.17,15.54a1,1,0,0,0,0,1.41,1,1,0,0,0,.71.29,1,1,0,0,0,.71-.29l4.24-4.24A1,1,0,0,0,14.83,11.29Z"></path></svg>
                      </div>
                    </div>
                    <el-dropdown-menu slot="dropdown" style="max-height:500px; overflow-y: scroll;">
                      <template v-if="router.path==='detail' && (router.type.startsWith('landmark') || router.type === 'extractIonCurrent')">
                        <el-dropdown-item v-for="option in [{'id':'0000', name:'All data'}].concat(landmarkData)" :key="option.id" :command=option.name style="font-size:12px;">{{ option.name }}</el-dropdown-item>
                      </template>
                      <template v-else>
                        <el-dropdown-item v-for="option in ['All data'].concat(ionCurrentDataFilterOptions)" :key="option" :command=option style="font-size:12px;">{{ option }}</el-dropdown-item>
                      </template>
                    </el-dropdown-menu>
                  </el-dropdown>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
          <vue-plotly :data="detailPanelData" :layout="detailPanelLayout" :options="defaultPlotOptions" style="height: calc(100% - 40px);"></vue-plotly>
        </div>
      </div>

      <div style="background-color: #f4f5f5;" v-if="router.path === 'editor'">
        <div style="display: flex; justify-content: space-between;">
          <el-button size="mini" @click="goBack" round style="margin: 0px 10px" class="back-button">
            <svg xmlns="http://www.w3.org/2000/svg" data-name="Layer 1" viewBox="0 0 24 24" width="30" height="30"><path d="M17,11H9.41l3.3-3.29a1,1,0,1,0-1.42-1.42l-5,5a1,1,0,0,0-.21.33,1,1,0,0,0,0,.76,1,1,0,0,0,.21.33l5,5a1,1,0,0,0,1.42,0,1,1,0,0,0,0-1.42L9.41,13H17a1,1,0,0,0,0-2Z"></path></svg>
          </el-button>
          <el-button size="mini" @click="toggleCollapseEditArea" class="collapse-button" style="margin: 0px 10px; padding: 0px 8px;">
            <svg v-show="!collapseEditArea" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="18" height="18" class="css-sr6nr"><path d="M14.83,11.29,10.59,7.05a1,1,0,0,0-1.42,0,1,1,0,0,0,0,1.41L12.71,12,9.17,15.54a1,1,0,0,0,0,1.41,1,1,0,0,0,.71.29,1,1,0,0,0,.71-.29l4.24-4.24A1,1,0,0,0,14.83,11.29Z"></path></svg>
            <svg v-show="collapseEditArea" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="18" height="18" class="css-sr6nr"><path d="M11.29,12l3.54-3.54a1,1,0,0,0,0-1.41,1,1,0,0,0-1.42,0L9.17,11.29a1,1,0,0,0,0,1.42L13.41,17a1,1,0,0,0,.71.29,1,1,0,0,0,.71-.29,1,1,0,0,0,0-1.41Z"></path></svg>
          </el-button>
        </div>
        <div style="display: flex;">
          <div style="margin: 10px;">
            <div class="panelToolsBar">
              <el-dropdown trigger="click">
                <span>{{ editorPanelTitle }}<i class="el-icon-arrow-down el-icon--right"></i></span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item style="padding: 0px 0px 0px 10px">
                    <el-dropdown placement="right-start" style="width:100%" @command="handleFilterEditorPanelData">
                      <div style="display:flex; justify-content: space-between;">
                        <div style="display:flex; align-items:center; width:120px">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-18xxn5p"><path d="M20.47,7.37s0,0,0-.08l-.06-.15a.71.71,0,0,0-.07-.09.94.94,0,0,0-.09-.12l-.09-.07L20,6.78l-7.5-4.63a1,1,0,0,0-1.06,0L4,6.78l-.09.08-.09.07a.94.94,0,0,0-.09.12.71.71,0,0,0-.07.09l-.06.15s0,0,0,.08a1.15,1.15,0,0,0,0,.26v8.74a1,1,0,0,0,.47.85l7.5,4.63h0a.47.47,0,0,0,.15.06s.05,0,.08,0a.86.86,0,0,0,.52,0s.05,0,.08,0a.47.47,0,0,0,.15-.06h0L20,17.22a1,1,0,0,0,.47-.85V7.63A1.15,1.15,0,0,0,20.47,7.37ZM11,19.21l-5.5-3.4V9.43L11,12.82Zm1-8.12L6.4,7.63,12,4.18l5.6,3.45Zm6.5,4.72L13,19.21V12.82l5.5-3.39Z"></path></svg>
                          <span style="margin-left:3px">Select Data</span>
                        </div>
                        <div style="display:flex; align-items:center;">
                          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" class="css-ejzmsl"><path d="M14.83,11.29,10.59,7.05a1,1,0,0,0-1.42,0,1,1,0,0,0,0,1.41L12.71,12,9.17,15.54a1,1,0,0,0,0,1.41,1,1,0,0,0,.71.29,1,1,0,0,0,.71-.29l4.24-4.24A1,1,0,0,0,14.83,11.29Z"></path></svg>
                        </div>
                      </div>
                      <el-dropdown-menu slot="dropdown" style="max-height:500px; overflow-y: scroll;">
                        <template v-if="router.type.startsWith('landmark') || router.type === 'extractIonCurrent'">
                          <el-dropdown-item v-for="option in [{'id':'0000', name:'All data'}].concat(landmarkData)" :key="option.id" :command=option.name style="font-size:12px;">{{ option.name }}</el-dropdown-item>
                        </template>
                        <template v-else>
                          <el-dropdown-item v-for="option in ['All data'].concat(ionCurrentDataFilterOptions)" :key="option" :command=option style="font-size:12px;">{{ option }}</el-dropdown-item>
                        </template>
                      </el-dropdown-menu>
                    </el-dropdown>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
            <vue-plotly :data="editorPanelData" :layout="editorPanelLayout" :options="defaultPlotOptions" style="height: calc(100% - 40px);"></vue-plotly>
          </div>
          <div class="edit-area" :style="{ width: layout.editor.width + 'px', height: layout.editor.height + 'px' }" v-show="!collapseEditArea">
            <el-form style="width: 100%;">
              <div>
                <div class="collapse-item-wrapper">
                  <div class="collapse-item">
                    <el-button size="mini" @click="collapseBasicPanelOptions = !collapseBasicPanelOptions" class="collapse-item-button">
                      <div style="display: flex;">
                        <svg v-show="!collapseBasicPanelOptions" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="18" height="18" class="css-ovd6k"><path d="M17,9.17a1,1,0,0,0-1.41,0L12,12.71,8.46,9.17a1,1,0,0,0-1.41,0,1,1,0,0,0,0,1.42l4.24,4.24a1,1,0,0,0,1.42,0L17,10.59A1,1,0,0,0,17,9.17Z"></path></svg>
                        <svg v-show="collapseBasicPanelOptions" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="18" height="18" class="css-ovd6k"><path d="M14.83,11.29,10.59,7.05a1,1,0,0,0-1.42,0,1,1,0,0,0,0,1.41L12.71,12,9.17,15.54a1,1,0,0,0,0,1.41,1,1,0,0,0,.71.29,1,1,0,0,0,.71-.29l4.24-4.24A1,1,0,0,0,14.83,11.29Z"></path></svg>
                      </div>
                    </el-button>
                    <a style="width: 100%;" @click="collapseBasicPanelOptions = !collapseBasicPanelOptions"><span class="collapse-item-text">Basic panel options</span></a>
                  </div>
                </div>
                <div class="collapse-area" v-show="!collapseBasicPanelOptions">
                  <el-row style="margin:8px 0px;">
                    <div style="display:flex; gap:8px;">
                      <div class="el-form-subitem-wrapper">
                        <span class="settings-label">Line width</span>
                      </div>
                      <el-select v-model="editorPanelOptions.lineWidth" size="mini"  @change="editPanel" style="width:80px">
                        <el-option v-for="item in lineWidthOptions" :key="item.value" :label="item.label" :value="item.value" size="mini"></el-option>
                      </el-select>
                    </div>
                  </el-row>
                  <el-row style="margin:8px 0px;">
                    <div style="display:flex; gap:8px;">
                      <div class="el-form-subitem-wrapper">
                        <span class="settings-label">Marker size of QC</span>
                      </div>
                      <el-select v-model="editorPanelOptions.markerSizeOfQC" size="mini" @change="editPanel" style="width:80px">
                        <el-option v-for="item in markerSizeOptions" :key="item.value" :label="item.label" :value="item.value" size="mini"></el-option>
                      </el-select>
                    </div>
                  </el-row>
                  <el-row style="margin:8px 0px;">
                    <div style="display:flex; gap:8px;">
                      <div class="el-form-subitem-wrapper">
                        <span class="settings-label">Marker size of Blank</span>
                      </div>
                      <el-select v-model="editorPanelOptions.markerSizeOfBlank" size="mini" @change="editPanel" style="width:80px">
                        <el-option v-for="item in markerSizeOptions" :key="item.value" :label="item.label" :value="item.value" size="mini"></el-option>
                      </el-select>
                    </div>
                  </el-row>
                  <el-row style="margin:8px 0px">
                    <div style="display:flex; gap:8px;">
                      <div class="el-form-subitem-wrapper">
                        <span class="settings-label">Marker shape of QC</span>
                      </div>
                      <div class="radio-group">
                        <input type="radio" class="radio-hidden" id="option-qc-marker-shape-diamond" name="markerShapeOfQC" value="diamond" v-model="editorPanelOptions.markerShapeOfQC" @change="editPanel">
                        <label class="radio-label" for="option-qc-marker-shape-diamond">
                          <svg t="1687413292449" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5932" width="8" height="8"><path d="M0.000827 512.005117L511.99571 0 1023.99571 512.005117 512.000827 1024z" p-id="5933"></path></svg>
                        </label>
                        <input type="radio" class="radio-hidden" id="option-qc-marker-shape-triangle-up" name="markerShapeOfQC" value="triangle-up" v-model="editorPanelOptions.markerShapeOfQC" @change="editPanel">
                        <label class="radio-label" for="option-qc-marker-shape-triangle-up">
                          <svg t="1687418997416" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1566" width="10" height="10"><path d="M168.704 810.666667h686.592a21.333333 21.333333 0 0 0 17.493333-33.493334L529.578667 281.344a21.333333 21.333333 0 0 0-35.072 0L151.168 777.173333a21.333333 21.333333 0 0 0 17.536 33.493334z" fill="#000000" p-id="1567"></path></svg>
                        </label>
                        <input type="radio" class="radio-hidden" id="option-qc-marker-shape-circle" name="markerShapeOfQC" value="" v-model="editorPanelOptions.markerShapeOfQC" @change="editPanel">
                        <label class="radio-label" for="option-qc-marker-shape-circle">
                          <svg t="1687419880496" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2495" width="8" height="8"><path d="M950.857143 512q0 119.428571-58.857143 220.285714T732.285714 892 512 950.857143t-220.285714-58.857143T132 732.285714 73.142857 512t58.857143-220.285714T291.714286 132 512 73.142857t220.285714 58.857143T892 291.714286 950.857143 512z" p-id="2496" fill="#2c2c2c"></path></svg>
                        </label>
                        <input type="radio" class="radio-hidden" id="option-qc-marker-shape-x" name="markerShapeOfQC" value="x" v-model="editorPanelOptions.markerShapeOfQC" @change="editPanel">
                        <label class="radio-label" for="option-qc-marker-shape-x">
                          <svg t="1687420128561" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4349" width="8" height="8"><path d="M180.48 0L0 180.48l92.16 92.16L320 504.32 92.16 732.16 0 820.48l180.48 184.32 92.16-92.16 231.68-231.68 227.84 231.68 88.32 92.16 184.32-184.32-92.16-88.32-231.68-227.84 231.68-231.68 92.16-92.16L820.48 0l-88.32 92.16L504.32 320 272.64 92.16 180.48 0z" p-id="4350" fill="#2c2c2c"></path></svg>
                        </label>
                      </div>
                    </div>
                  </el-row>
                  <el-row style="margin:8px 0px">
                    <div style="display:flex; gap:8px;">
                      <div class="el-form-subitem-wrapper">
                        <span class="settings-label">Marker shape of Blank</span>
                      </div>
                      <div class="radio-group">
                        <input type="radio" class="radio-hidden" id="option-blank-marker-shape-diamond" value="diamond" name="markerShapeOfBlank" v-model="editorPanelOptions.markerShapeOfBlank" @change="editPanel">
                        <label class="radio-label" for="option-blank-marker-shape-diamond">
                          <svg t="1687413292449" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5932" width="8" height="8"><path d="M0.000827 512.005117L511.99571 0 1023.99571 512.005117 512.000827 1024z" p-id="5933"></path></svg>
                        </label>
                        <input type="radio" class="radio-hidden" id="option-blank-marker-shape-triangle-up" value="triangle-up" name="markerShapeOfBlank" v-model="editorPanelOptions.markerShapeOfBlank" @change="editPanel">
                        <label class="radio-label" for="option-blank-marker-shape-triangle-up">
                          <svg t="1687418997416" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1566" width="10" height="10"><path d="M168.704 810.666667h686.592a21.333333 21.333333 0 0 0 17.493333-33.493334L529.578667 281.344a21.333333 21.333333 0 0 0-35.072 0L151.168 777.173333a21.333333 21.333333 0 0 0 17.536 33.493334z" fill="#000000" p-id="1567"></path></svg>
                        </label>
                        <input type="radio" class="radio-hidden" id="option-blank-marker-shape-circle" value="" name="markerShapeOfBlank" v-model="editorPanelOptions.markerShapeOfBlank" @change="editPanel">
                        <label class="radio-label" for="option-blank-marker-shape-circle">
                          <svg t="1687419880496" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2495" width="8" height="8"><path d="M950.857143 512q0 119.428571-58.857143 220.285714T732.285714 892 512 950.857143t-220.285714-58.857143T132 732.285714 73.142857 512t58.857143-220.285714T291.714286 132 512 73.142857t220.285714 58.857143T892 291.714286 950.857143 512z" p-id="2496" fill="#2c2c2c"></path></svg>
                        </label>
                        <input type="radio" class="radio-hidden" id="option-blank-marker-shape-x" value="x" name="markerShapeOfBlank" v-model="editorPanelOptions.markerShapeOfBlank" @change="editPanel">
                        <label class="radio-label" for="option-blank-marker-shape-x">
                          <svg t="1687420128561" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4349" width="8" height="8"><path d="M180.48 0L0 180.48l92.16 92.16L320 504.32 92.16 732.16 0 820.48l180.48 184.32 92.16-92.16 231.68-231.68 227.84 231.68 88.32 92.16 184.32-184.32-92.16-88.32-231.68-227.84 231.68-231.68 92.16-92.16L820.48 0l-88.32 92.16L504.32 320 272.64 92.16 180.48 0z" p-id="4350" fill="#2c2c2c"></path></svg>
                        </label>
                      </div>
                    </div>
                  </el-row>
                  <el-row style="margin:8px 0px">
                    <div style="display:flex; gap:8px; align-items: center;">
                      <div class="el-form-subitem-wrapper">
                        <span class="settings-label">Marker color of QC</span>
                      </div>
                      <v-swatches v-model="editorPanelOptions.markerColorOfQC" :swatches="swatches" show-fallback popover-x="left" @input="editPanel"></v-swatches>
                    </div>
                  </el-row>
                  <el-row style="margin:8px 0px">
                    <div style="display:flex; gap:8px; align-items: center;">
                      <div class="el-form-subitem-wrapper">
                        <span class="settings-label">Marker color of Blank</span>
                      </div>
                      <v-swatches v-model="editorPanelOptions.markerColorOfBlank" :swatches="swatches" show-fallback popover-x="left" @input="editPanel"></v-swatches>
                    </div>
                  </el-row>
                </div>
              </div>
              <div>
                <div class="collapse-item-wrapper">
                  <div class="collapse-item">
                    <el-button size="mini" @click="collapseAdvancedPanelOptions = !collapseAdvancedPanelOptions" class="collapse-item-button">
                      <div style="display: flex;">
                        <svg v-show="!collapseAdvancedPanelOptions" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="18" height="18" class="css-ovd6k"><path d="M17,9.17a1,1,0,0,0-1.41,0L12,12.71,8.46,9.17a1,1,0,0,0-1.41,0,1,1,0,0,0,0,1.42l4.24,4.24a1,1,0,0,0,1.42,0L17,10.59A1,1,0,0,0,17,9.17Z"></path></svg>
                        <svg v-show="collapseAdvancedPanelOptions" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="18" height="18" class="css-ovd6k"><path d="M14.83,11.29,10.59,7.05a1,1,0,0,0-1.42,0,1,1,0,0,0,0,1.41L12.71,12,9.17,15.54a1,1,0,0,0,0,1.41,1,1,0,0,0,.71.29,1,1,0,0,0,.71-.29l4.24-4.24A1,1,0,0,0,14.83,11.29Z"></path></svg>
                      </div>
                    </el-button>
                    <a style="width: 100%;" @click="collapseAdvancedPanelOptions = !collapseAdvancedPanelOptions"><span class="collapse-item-text">Advanced panel options</span></a>
                  </div>
                </div>
                <div class="collapse-area" v-show="!collapseAdvancedPanelOptions">
                  <el-row style="margin:8px 0px">
                    <div style="display:flex; gap:8px; align-items: center;">
                      <div class="el-form-subitem-wrapper">
                        <span class="settings-label">Show QL</span>
                      </div>
                      <el-switch v-model="editorPanelOptions.showQL" active-color="#3871dc" inactive-color="lightgray" style="width:32px; height:16px;" v-on:change="renderWLQL(editorPanelData, router.type, editorPanelOptions)"></el-switch>
                    </div>
                  </el-row>
                  <div v-if="router.path === 'editor' && router.type.startsWith('landmark')">
                    <div v-show="editorPanelOptions.showQL">
                      <el-row style="margin:8px 0px">
                        <div style="display:flex; gap:8px; align-items: center;">
                          <div class="el-form-subitem-wrapper">
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkRt'">UQL(min)</span>
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkIntensity'">UQL(std)</span>
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkRelMzError'">UQL(ppm)</span>
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkAbsMzError'">UQL(mDa)</span>
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkPeakArea'">UQL(std)</span>
                          </div>
                          <el-input-number v-model="editorPanelOptions.UQL" :step="0.1" size="mini" style="width: 110px;" v-on:change="renderWLQL(editorPanelData, router.type, editorPanelOptions)"></el-input-number>
                        </div>
                      </el-row>
                      <el-row style="margin:8px 0px">
                        <div style="display:flex; gap:8px; align-items: center;">
                          <div class="el-form-subitem-wrapper">
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkRt'">BQL(min)</span>
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkIntensity'">BQL(std)</span>
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkRelMzError'">BQL(ppm)</span>
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkAbsMzError'">BQL(mDa)</span>
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkPeakArea'">BQL(std)</span>
                          </div>
                          <el-input-number v-model="editorPanelOptions.BQL" :step="0.1" size="mini" style="width: 110px;" v-on:change="renderWLQL(editorPanelData, router.type, editorPanelOptions)"></el-input-number>
                        </div>
                      </el-row>
                      <el-row style="margin:8px 0px;">
                        <div style="display:flex; gap:8px;">
                          <div class="el-form-subitem-wrapper">
                            <span class="settings-label">Line width of QL</span>
                          </div>
                          <el-select v-model="editorPanelOptions.lineWidthOfQL" size="mini"  @change="renderWLQL(editorPanelData, router.type, editorPanelOptions)" style="width:80px">
                            <el-option v-for="item in lineWidthOptions" :key="item.value" :label="item.label" :value="item.value" size="mini"></el-option>
                          </el-select>
                        </div>
                      </el-row>
                      <el-row style="margin:8px 0px">
                        <div style="display:flex; gap:8px; align-items: center;">
                          <div class="el-form-subitem-wrapper">
                            <span class="settings-label">Line color of QL</span>
                          </div>
                          <v-swatches v-model="editorPanelOptions.lineColorOfQL" show-fallback popover-x="left" v-on:input="renderWLQL(editorPanelData, router.type, editorPanelOptions)"></v-swatches>
                        </div>
                      </el-row>
                    </div>
                  </div>
                  <el-row style="margin:8px 0px">
                    <div style="display:flex; gap:8px; align-items: center;">
                      <div class="el-form-subitem-wrapper">
                        <span class="settings-label">Show WL</span>
                      </div>
                      <el-switch v-model="editorPanelOptions.showWL" active-color="#3871dc" inactive-color="lightgray" style="width:32px; height:16px;" v-on:change="renderWLQL(editorPanelData, router.type, editorPanelOptions)"></el-switch>
                    </div>
                  </el-row>
                  <div v-if="router.path==='editor' && router.type.startsWith('landmark')">
                    <div v-show="editorPanelOptions.showWL">
                      <el-row style="margin:8px 0px">
                        <div style="display:flex; gap:8px; align-items: center;">
                          <div class="el-form-subitem-wrapper">
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkRt'">UWL(min)</span>
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkIntensity'">UWL(std)</span>
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkRelMzError'">UWL(ppm)</span>
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkAbsMzError'">UWL(mDa)</span>
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkPeakArea'">UWL(std)</span>
                          </div>
                          <el-input-number v-model="editorPanelOptions.UWL" :step="0.1" size="mini" style="width: 110px;" v-on:change="renderWLQL(editorPanelData, router.type, editorPanelOptions)"></el-input-number>
                        </div>
                      </el-row>
                      <el-row style="margin:8px 0px">
                        <div style="display:flex; gap:8px; align-items: center;">
                          <div class="el-form-subitem-wrapper">
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkRt'">BWL(min)</span>
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkIntensity'">BWL(std)</span>
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkRelMzError'">BWL(ppm)</span>
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkAbsMzError'">BWL(mDa)</span>
                            <span class="settings-label" v-if="router.path==='editor' && router.type==='landmarkPeakArea'">BWL(std)</span>
                          </div>
                          <el-input-number v-model="editorPanelOptions.BWL" :step="0.1" size="mini" style="width: 110px;" v-on:change="renderWLQL(editorPanelData, router.type, editorPanelOptions)"></el-input-number>
                        </div>
                      </el-row>
                      <el-row style="margin:8px 0px;">
                        <div style="display:flex; gap:8px;">
                          <div class="el-form-subitem-wrapper">
                            <span class="settings-label">Line width of WL</span>
                          </div>
                          <el-select v-model="editorPanelOptions.lineWidthOfWL" size="mini"  @change="renderWLQL(editorPanelData, router.type, editorPanelOptions)" style="width:80px">
                            <el-option v-for="item in lineWidthOptions" :key="item.value" :label="item.label" :value="item.value" size="mini"></el-option>
                          </el-select>
                        </div>
                      </el-row>
                      <el-row style="margin:8px 0px">
                        <div style="display:flex; gap:8px; align-items: center;">
                          <div class="el-form-subitem-wrapper">
                            <span class="settings-label">Line color of WL</span>
                          </div>
                          <v-swatches v-model="editorPanelOptions.lineColorOfWL" show-fallback popover-x="left" v-on:input="renderWLQL(editorPanelData, router.type, editorPanelOptions)"></v-swatches>
                        </div>
                      </el-row>
                    </div>
                  </div>
                </div>
              </div>
            </el-form>
          </div>
        </div>
      </div>
    </div>

    <el-dialog title="Choose monitor directory" :visible.sync="selectMonitorFileDialogVisible" custom-class="custom-dilaog" width="800px" class="monitor-dialog">
      <div>
        <ul id="tree" class="ztree"></ul>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="selectMonitorFileDialogVisible = false">Cancel</el-button>
        <el-button type="primary" size="mini" @click="monitorFileConfirm" class="primary-btn">OK</el-button>
      </div>
    </el-dialog>

    <el-dialog title="settings" :visible.sync="settingDialogVisible" custom-class="custom-dilaog" width="800px">
      <el-tabs type="border-card">
        <el-tab-pane label="Basic">
          <el-form ref="peakExtractionForm" :model="peakExtractionForm" label-position="right" label-width="80px">
            <fieldset>
              <legend>Peak Extraction</legend>
              <el-form-item label="rt">
                <el-input-number v-model="peakExtractionForm.rt" size="mini"></el-input-number>
                <el-select v-model="peakExtractionForm.rtUnit" size="mini" style="width: 100px;">
                  <el-option v-for="item in peakExtractionForm.rtUnitOptions" :key="item" :value="item"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="m/z">
                <el-input-number v-model="peakExtractionForm.mzRatio" size="mini"></el-input-number>
                <el-select v-model="peakExtractionForm.mzRatioUnit" size="mini" style="width: 100px;">
                  <el-option v-for="item in peakExtractionForm.mzRatioUnitOptions" :key="item" :value="item"></el-option>
                </el-select>
              </el-form-item>
            </fieldset>
          </el-form>
          <el-form ref="sampleTypeAssignmentForm" :model="sampleTypeAssignmentForm" label-position="right" label-width="265px" style="margin-top: 30px">
            <fieldset>
              <legend>Sample Type Assignment</legend>
              <el-form-item label="Keyword of QC-Sample (Ignore Case)">
                <el-input v-model="sampleTypeAssignmentForm.keywordQC" size="mini" clearable style="width:150px;"></el-input>
              </el-form-item>
              <el-form-item label="Keyword of Blank-Sample (Ignore Case)">
                <el-input v-model="sampleTypeAssignmentForm.keywordBlank" size="mini" clearable style="width:150px;"></el-input>
              </el-form-item>
            </fieldset>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="Landmark">
          <div>
            <upload-excel :on-success="handleSuccess" :before-upload="beforeUpload" />
            <el-table :data="landmarkData" max-height="400" border fit highlight-current-row style="width: 100%; margin-top: 10px; margin-bottom: 10px;">
              <el-table-column align="left" label="Name" width="280">
                <template slot-scope="scope">
                  <template v-if="scope.row.edit">
                    <el-input v-model="scope.row.name" class="edit-input" size="mini" />
                  </template>
                  <span v-else>{{ scope.row.name }}</span>
                </template>
              </el-table-column>
              <el-table-column align="left" label="RT">
                <template slot-scope="scope">
                  <template v-if="scope.row.edit">
                    <el-input v-model="scope.row.rt" size="mini" style="width:100px;"></el-input>
                    <el-select v-model="scope.row.rtUnit" size="mini" style="width: 70px;">
                      <el-option v-for="item in rtUnitOptions" :key="item" :value="item"></el-option>
                    </el-select>
                  </template>
                  <span v-else>{{ scope.row.rt + " " + scope.row.rtUnit }}</span>
                </template>
              </el-table-column>
              <el-table-column align="left" label="Theoretical m/z">
                <template slot-scope="scope">
                  <template v-if="scope.row.edit">
                    <el-input v-model="scope.row.mzRatio" size="mini" style="width:100px;"></el-input>
                  </template>
                  <span v-else>{{ scope.row.mzRatio }}</span>
                </template>
              </el-table-column>
              <el-table-column label="Polarity" width="80">
                <template slot-scope="scope">
                  <template v-if="scope.row.edit">
                    <el-select v-model="scope.row.polarity" size="mini">
                      <el-option v-for="item in ['+', '-']" :key="item" :value="item"></el-option>
                    </el-select>
                  </template>
                  <span v-else>{{ scope.row.polarity }}</span>
                </template>
              </el-table-column>

              <el-table-column align="center" label="Actions" width="130">
                <template slot-scope="scope">
                  <el-button v-if="scope.row.edit" type="success" size="mini" icon="el-icon-check" @click="confirmEditLandmark(scope.row)"></el-button>
                  <el-button v-if="scope.row.edit" type="warning" size="mini" icon="el-icon-refresh" style="background-color: #ffc465; border-color: #ffc465;"
                    @click="cancelEditLandmark(scope.row)"></el-button>
                  <el-button v-if="!scope.row.edit" type="primary" size="mini" icon="el-icon-edit" @click="scope.row.edit=!scope.row.edit"></el-button>
                  <el-button v-if="!scope.row.edit" type="danger" size="mini" style = "background-color: #c40000; border-color: #c40000;" icon="el-icon-delete"
                    @click="removeLandmark(scope.$index)"></el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-button type="plain" size="mini" icon="iconfont icon-plus" class="plus-button" @click="addLandmark()"></el-button>
          </div>
        </el-tab-pane>
        <el-tab-pane label="Advanced">
          <div></div>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>

    <el-dialog title="Save project" :visible.sync="saveProjectDialogVisible" custom-class="custom-dilaog" width="500px">
      <el-form size="mini" label-width="125px">
        <el-form-item label="Project name">
          <el-input v-model="project.name" class="input-outline"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSaveProject" class="primary-btn">Save</el-button>
          <el-button @click="saveProjectDialogVisible = false">Cancel</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-drawer title="Recent projects" :visible.sync="openProjectDrawerVisible" direction="rtl" size="40%">
      <hr class="divider-line">
      <div id="table-projects">
        <el-table :data="projects" border style="width:100%" :row-class-name="projectRowClassName">
          <el-table-column prop="createTime" label="Create time" width="160"></el-table-column>
          <el-table-column prop="name" label="Name"></el-table-column>
          <el-table-column prop="sampleNumber" label="Sample number" width="130"></el-table-column>
          <el-table-column prop="status" label="Status" width="90"></el-table-column>
          <el-table-column label="Actions" width="90">
              <template slot-scope="scope">
                <div style="display: flex;">
                  <el-tooltip class="item" effect="dark" content="Open project" placement="bottom">
                    <el-button type="primary" size="mini" @click="loadProject(scope.row.id)" style="display: flex; align-items: center; padding: 2px 6px;">
                      <svg t="1739331978219" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="10781" width="18" height="18"><path d="M1000.106667 375.466667l-129.706667-167.253334c-3.413333-3.413333-6.826667-6.826667-10.24-6.826666L515.413333 160.426667H512h-3.413333L160.426667 201.386667c-3.413333 0-6.826667 3.413333-10.24 6.826666L20.48 372.053333c-3.413333 6.826667-3.413333 13.653333 0 20.48 3.413333 6.826667 10.24 10.24 13.653333 10.24h3.413334l348.16-40.96c3.413333 0 10.24-3.413333 10.24-6.826666L512 204.8l116.053333 153.6c3.413333 3.413333 6.826667 6.826667 10.24 6.826667L986.453333 409.6h3.413334c10.24 0 17.066667-6.826667 17.066666-17.066667 0-6.826667-3.413333-13.653333-6.826666-17.066666z" fill="#ffffff" p-id="10782"></path><path d="M484.693333 314.026667c-6.826667-3.413333-13.653333 0-20.48 6.826666L419.84 375.466667c-6.826667 10.24-20.48 17.066667-34.133333 20.48l-218.453334 27.306666c-10.24 0-13.653333 6.826667-13.653333 17.066667v293.546667c0 6.826667 3.413333 13.653333 10.24 17.066666l307.2 116.053334h6.826667c3.413333 0 6.826667 0 10.24-3.413334 3.413333-3.413333 6.826667-6.826667 6.826666-13.653333V327.68c0-6.826667-3.413333-13.653333-10.24-13.653333z m-30.72 351.573333c-3.413333 3.413333-6.826667 3.413333-13.653333 3.413333s-10.24 0-13.653333-3.413333l-37.546667-37.546667v95.573334c0 10.24-6.826667 17.066667-17.066667 17.066666s-17.066667-6.826667-17.066666-17.066666v-95.573334l-37.546667 37.546667c-6.826667 6.826667-17.066667 6.826667-23.893333 0s-6.826667-17.066667 0-23.893333l68.266666-68.266667s3.413333-3.413333 6.826667-3.413333c3.413333-3.413333 10.24-3.413333 13.653333 0 3.413333 0 3.413333 3.413333 6.826667 3.413333l68.266667 68.266667c3.413333 6.826667 3.413333 17.066667-3.413334 23.893333zM856.746667 426.666667l-218.453334-27.306667c-13.653333 0-27.306667-10.24-34.133333-20.48l-40.96-54.613333c-3.413333-6.826667-13.653333-6.826667-20.48-6.826667-6.826667 3.413333-10.24 10.24-10.24 17.066667v512c0 6.826667 3.413333 10.24 6.826667 13.653333 3.413333 3.413333 6.826667 3.413333 10.24 3.413333h6.826666l307.2-109.226666c6.826667-3.413333 10.24-10.24 10.24-17.066667V443.733333c-3.413333-10.24-10.24-17.066667-17.066666-17.066666z m-119.466667 279.893333l-119.466667 34.133333H614.4c-6.826667 0-13.653333-3.413333-17.066667-13.653333-3.413333-10.24 3.413333-17.066667 10.24-20.48l119.466667-34.133333c10.24-3.413333 17.066667 3.413333 20.48 10.24 3.413333 10.24 0 20.48-10.24 23.893333z" fill="#ffffff" p-id="10783"></path></svg>
                    </el-button>
                  </el-tooltip>
                  <el-tooltip class="item" effect="dark" content="Delete project" placement="bottom">
                    <el-button type="danger" size="mini" style="background-color: #c40000; border-color: #c40000; padding: 2px 8px;" icon="el-icon-delete" @click="deleteProject(scope.row)"></el-button>
                  </el-tooltip>
                </div>
              </template>
          </el-table-column>
        </el-table>
      </div>
    </el-drawer>

  </div>
</template>

<script>
import $ from 'jquery'
import '@/lib/ztree/js/jquery.ztree.core.js'
import '@/lib/ztree/js/jquery.ztree.excheck.js'
import '@/lib/ztree/css/zTreeStyle/zTreeStyle.css'
import UploadExcel from '@/components/UploadExcel'
import VuePlotly from '@statnett/vue-plotly'
import VSwatches from 'vue-swatches'
import 'vue-swatches/dist/vue-swatches.css'
import { GridLayout, GridItem } from 'vue-grid-layout'
import html2canvas from 'html2canvas'

// 开发环境
// const API_BASE = 'api'

// 生产环境
const API_BASE = ''

const HEADER_HEIGHT = 70
const SIDEBAR_WIDTH = 150
const EDITOR_WIDTH = 300

export default {
  name: 'QCMaster',
  components: { UploadExcel, VuePlotly, GridLayout, GridItem, VSwatches },
  data () {
    return {
      progressGraph: null,
      selectMonitorFileDialogVisible: false,
      settingDialogVisible: false,
      zTreeObj: null,
      monitorTask: {
        taskId: null,
        monitorPath: '',
        monitorSamples: [],
        monitoring: false,
        serialNumber: 0
      },
      eventSource: null,
      // 隐藏侧边栏
      viewMode: 'default',
      router: { path: 'overview', type: '' },
      // panel data
      basePeakCurrentData: [],
      totalIonCurrentData: [],
      extractIonCurrentData: [],
      landmarkRtData: [],
      landmarkIntensityData: [],
      landmarkRelMzErrorData: [],
      landmarkAbsMzErrorData: [],
      landmarkPeakAreaData: [],
      // panel title
      currentChromatogram: 'Base Peak Chromatogram',
      switchChromatogram1: 'Total Ion Chromatogram',
      switchChromatogram2: 'Extract Ion Chromatogram',
      panelTitleOfRT: 'RT',
      panelTitleOfIntensity: 'Peak Intensity',
      panelTitleOfRelMzError: 'Relative m/z Error',
      panelTitleOfAbsMzError: 'Absolute m/z Error',
      panelTitleOfPeakArea: 'Peak Area',
      // panel options
      ionCurrentPanelOptions: {
        lineWidth: 1,
        markerSizeOfQC: 6,
        markerSizeOfBlank: 4,
        markerShapeOfQC: 'diamond',
        markerShapeOfBlank: ''
      },
      panelOptions: {},
      // 调色板
      swatches: ['#1fbc9c', '#1ca085', '#2ecc70', '#3cb770', '#3398db', '#2980b9', '#a463bf', '#8e43ad', '#3d556e', '#222f3d', '#f2c511', '#f39c19', '#e84b3c', '#c0382b', '#bdc3c8', ''],
      // 基本参数
      peakExtractionForm: {
        rt: 1,
        rtUnit: 'min',
        rtUnitOptions: ['min', 's'],
        mzRatio: 10,
        mzRatioUnit: 'ppm',
        mzRatioUnitOptions: ['ppm', 'mDa'],
        peakWidth: 0.30,
        peakWidthUnit: 'min',
        peakWidthUnitOptions: ['min', 's']
      },
      sampleTypeAssignmentForm: {
        keywordQC: 'QC',
        keywordBlank: 'Blank'
      },
      rtUnitOptions: ['min', 's'],
      landmarkData: [],
      // overview
      ionCurrentData: [],
      // ion current数据过滤选择option
      ionCurrentDataFilterOptions: [],
      selectedIonCurrentData: [],
      selectedSampleOfIonCurrentData: 'All data',
      selectedLandmarkRtData: [],
      selectedLandmarkOfRt: 'All data',
      selectedLandmarkIntensityData: [],
      selectedLandmarkOfIntensity: 'All data',
      selectedLandmarkRelMzErrorData: [],
      selectedLandmarkOfRelMzError: 'All data',
      selectedLandmarkAbsMzErrorData: [],
      selectedLandmarkOfAbsMzError: 'All data',
      selectedLandmarkPeakAreaData: [],
      selectedLandmarkOfPeakArea: 'All data',
      defaultPlotOptions: {
        displayModeBar: false,
        displaylogo: false
      },
      // panel layout
      layout: {
        header: { width: null, height: null },
        sidebar: { width: null, height: null },
        main: { width: null, height: null },
        panel: { width: null, height: null },
        editor: { width: null, height: null }
      },
      ionCurrentLayout: {
        showlegend: false,
        margin: { t: 40, b: 50, l: 70, r: 40 },
        xaxis: {
          title: { text: 'RT(min)', standoff: 2 }
        },
        yaxis: { exponentformat: 'E', showline: true, title: { text: 'Intensity', standoff: 2 }, fixedrange: true },
        width: null,
        height: null
      },
      landmarkRtLayout: {
        showlegend: false,
        margin: { t: 40, b: 50, l: 70, r: 40 },
        xaxis: { title: { text: 'Injection Sequence', standoff: 2 } },
        yaxis: { exponentformat: 'E', showline: true, title: { text: 'RT(min)', standoff: 2 }, fixedrange: true },
        width: null,
        height: null,
        shapes: []
      },
      landmarkIntensityLayout: {
        showlegend: false,
        margin: { t: 40, b: 50, l: 70, r: 40 },
        xaxis: { title: { text: 'Injection Sequence', standoff: 2 } },
        yaxis: { exponentformat: 'E', showline: true, title: { text: 'Intensity', standoff: 2 }, fixedrange: true },
        width: null,
        height: null,
        shapes: []
      },
      landmarkRelMzErrorLayout: {
        showlegend: false,
        margin: { t: 40, b: 50, l: 70, r: 40 },
        xaxis: { title: { text: 'Injection Sequence', standoff: 2 } },
        yaxis: { exponentformat: 'E', showline: true, title: { text: 'Relative Error(ppm)', standoff: 2 }, fixedrange: true },
        width: null,
        height: null,
        shapes: []
      },
      landmarkAbsMzErrorLayout: {
        showlegend: false,
        margin: { t: 40, b: 50, l: 70, r: 40 },
        xaxis: { title: { text: 'Injection Sequence', standoff: 2 } },
        yaxis: { exponentformat: 'E', showline: true, title: { text: 'Absolute Error(mDa)', standoff: 2 }, fixedrange: true },
        width: null,
        height: null,
        shapes: []
      },
      landmarkPeakAreaLayout: {
        showlegend: false,
        margin: { t: 40, b: 50, l: 75, r: 40 },
        xaxis: { title: { text: 'Injection Sequence', standoff: 2 } },
        yaxis: { exponentformat: 'E', showline: true, title: { text: 'Peak Area', standoff: 2 }, fixedrange: true },
        width: null,
        height: null,
        shapes: []
      },
      gridLayout: [
        { x: 0, y: 0, w: 16, h: 20, i: 0, static: false },
        { x: 16, y: 0, w: 16, h: 20, i: 1, static: false },
        { x: 32, y: 0, w: 16, h: 20, i: 2, static: false },
        { x: 0, y: 1, w: 16, h: 20, i: 3, static: false },
        { x: 16, y: 1, w: 16, h: 20, i: 4, static: false },
        { x: 32, y: 1, w: 16, h: 20, i: 5, static: false }
      ],
      draggable: true,
      resizable: true,
      gridRowHeight: null,
      gridColWidth: null,
      /**
       * detail panel
       */
      detailPanelData: [],
      detailPanelLayout: {
        showlegend: false,
        margin: { t: 40, b: 50, l: 70, r: 40 },
        xaxis: { title: { text: 'Injection Sequence', standoff: 2 } },
        yaxis: { exponentformat: 'E', showline: true, title: { text: 'RT(min)', standoff: 2 }, fixedrange: true },
        width: null,
        height: null,
        shapes: []
      },
      detailPanelTitle: '',
      editDetailPanelCommand: '',
      /**
       * panel editor
       */
      editorPanelData: [],
      editorPanelLayout: {
        showlegend: false,
        margin: { t: 40, b: 40, l: 60, r: 40 },
        yaxis: { exponentformat: 'E' },
        width: null,
        height: null,
        shapes: []
      },
      editorPanelTitle: '',
      editorPanelDataFilterOptions: [],
      editorPanelOptions: {
        lineWidth: 1,
        markerSizeOfQC: 1,
        markerSizeOfBlank: 1,
        markerShapeOfQC: '',
        markerShapeOfBlank: '',
        markerColorOfQC: '',
        markerColorOfBlank: '',
        showQL: true,
        showWL: true,
        UQL: 0.5,
        BQL: -0.5,
        UWL: 1,
        BWL: -1,
        lineWidthOfQL: 1,
        lineWidthOfWL: 1,
        lineColorOfQL: 'orange',
        lineColorOfWL: 'orange'
      },
      lineWidthOptions: [
        { label: '1', value: 1 },
        { label: '2', value: 2 },
        { label: '3', value: 3 },
        { label: '4', value: 4 },
        { label: '5', value: 5 },
        { label: '6', value: 6 },
        { label: '7', value: 7 },
        { label: '8', value: 8 },
        { label: '9', value: 9 },
        { label: '10', value: 10 }
      ],
      markerSizeOptions: [
        { label: '1', value: 1 },
        { label: '2', value: 2 },
        { label: '3', value: 3 },
        { label: '4', value: 4 },
        { label: '5', value: 5 },
        { label: '6', value: 6 },
        { label: '7', value: 7 },
        { label: '8', value: 8 },
        { label: '9', value: 9 },
        { label: '10', value: 10 }
      ],
      collapseEditArea: false,
      collapseBasicPanelOptions: false,
      collapseAdvancedPanelOptions: false,
      collapseSampleSelectionDropdownMenu: false,
      collapseLandmarkSelectionDropdownMenu: false,
      selectedLastNSamples: { label: 'All samples', value: 'All samples' },
      sampleDropdownMenuOptions: [ { label: 'All samples', value: 'All samples' }, { label: 'Last 20 samples', value: 20 }, { label: 'Last 50 samples', value: 50 },
        { label: 'Last 100 samples', value: 100 }, { label: 'Last 200 samples', value: 200 }, { label: 'Last 500 samples', value: 500 }],
      selectedLandmark: 'All landmarks',
      // project
      saveProjectDialogVisible: false,
      openProjectDrawerVisible: false,
      project: {
        name: 'New project'
      },
      projects: []
    }
  },
  mounted () {
    // this.loadMonitorDirTree()
    this.loadLandmarks()
    this.loadGridLayout()
    this.loadPanelOptions('default')

    // 监听Esc按键被按下
    var self = this
    $(document).keydown(function (event) {
      if (event.keyCode === 27) {
        if (self.viewMode === 'hideSidebar') {
          self.viewMode = 'default'
        } else if (self.viewMode === 'hideHeader') {
          self.viewMode = 'default'
        }
      }
    })

    // initial layout
    this.layout.header.height = HEADER_HEIGHT
    this.layout.header.width = document.body.clientWidth - SIDEBAR_WIDTH
    this.layout.sidebar.height = document.body.clientHeight
    this.layout.sidebar.width = SIDEBAR_WIDTH
    this.layout.main.height = document.body.clientHeight - HEADER_HEIGHT
    this.layout.main.width = document.body.clientWidth - SIDEBAR_WIDTH
    this.layout.panel.height = (document.body.clientHeight - HEADER_HEIGHT - 10) / 2 - 40
    this.layout.panel.width = (document.body.clientWidth - SIDEBAR_WIDTH - 40) / 3
    this.ionCurrentLayout.height = this.layout.panel.height
    this.ionCurrentLayout.width = this.layout.panel.width
    this.landmarkRtLayout.height = this.layout.panel.height
    this.landmarkRtLayout.width = this.layout.panel.width
    this.landmarkIntensityLayout.height = this.layout.panel.height
    this.landmarkIntensityLayout.width = this.layout.panel.width
    this.landmarkRelMzErrorLayout.height = this.layout.panel.height
    this.landmarkRelMzErrorLayout.width = this.layout.panel.width
    this.landmarkAbsMzErrorLayout.height = this.layout.panel.height
    this.landmarkAbsMzErrorLayout.width = this.layout.panel.width
    this.landmarkPeakAreaLayout.height = this.layout.panel.height
    this.landmarkPeakAreaLayout.width = this.layout.panel.width
    // grid layout
    this.gridRowHeight = (this.layout.main.height - 390) / 40
    this.gridColWidth = (this.layout.main.width - 470) / 48
    // detail panel
    this.detailPanelLayout.width = document.body.clientWidth - SIDEBAR_WIDTH - 20
    this.detailPanelLayout.height = document.body.clientHeight - HEADER_HEIGHT - 10 - 32 - 40
    // editor panel
    this.editorPanelLayout.width = document.body.clientWidth - SIDEBAR_WIDTH - 20 - EDITOR_WIDTH
    this.editorPanelLayout.height = document.body.clientHeight - HEADER_HEIGHT - 10 - 32 - 40
    this.layout.editor.width = 300
    this.layout.editor.height = (document.body.clientHeight - HEADER_HEIGHT - 10 - 32)

    // 动态调整sidebar的高度
    setInterval(() => {
      self.layout.sidebar.height = document.body.scrollHeight
    }, 2000)
  },
  watch: {
    'viewMode': function (to, from) {
      if (this.viewMode === 'default') {
        this.layout.header.width = document.body.clientWidth - SIDEBAR_WIDTH
        this.layout.main.width = document.body.clientWidth - SIDEBAR_WIDTH
        this.layout.main.height = document.body.clientHeight - HEADER_HEIGHT
        // detail panel
        this.detailPanelLayout.width = document.body.clientWidth - SIDEBAR_WIDTH - 20
        this.detailPanelLayout.height = document.body.clientHeight - HEADER_HEIGHT - 10 - 32 - 40
        // editor panel
        this.editorPanelLayout.width = document.body.clientWidth - SIDEBAR_WIDTH - 20 - EDITOR_WIDTH
        this.editorPanelLayout.height = document.body.clientHeight - HEADER_HEIGHT - 10 - 32 - 40
        this.layout.editor.height = (document.body.clientHeight - HEADER_HEIGHT - 10 - 32)
      } else if (this.viewMode === 'hideSidebar') {
        this.layout.header.width = document.body.clientWidth
        this.layout.main.width = document.body.clientWidth - 20
        this.layout.main.height = document.body.clientHeight - HEADER_HEIGHT
        // detail panel
        this.detailPanelLayout.width = document.body.clientWidth
        this.detailPanelLayout.height = document.body.clientHeight - HEADER_HEIGHT - 10 - 32 - 40
        // editor panel
        this.editorPanelLayout.width = document.body.clientWidth - 20 - EDITOR_WIDTH
        this.editorPanelLayout.height = document.body.clientHeight - HEADER_HEIGHT - 10 - 32 - 40
        this.layout.editor.height = (document.body.clientHeight - HEADER_HEIGHT - 10 - 32)
      } else if (this.viewMode === 'hideHeader') {
        this.layout.header.width = document.body.clientWidth
        this.layout.main.width = document.body.clientWidth
        this.layout.main.height = document.body.clientHeight
        // detail panel
        this.detailPanelLayout.width = document.body.clientWidth - 20
        this.detailPanelLayout.height = document.body.clientHeight - 10 - 32 - 40
        // editor panel
        this.editorPanelLayout.width = document.body.clientWidth - 20 - EDITOR_WIDTH
        this.editorPanelLayout.height = document.body.clientHeight - 10 - 32 - 40
        this.layout.editor.height = (document.body.clientHeight - 10 - 32)
      }
      this.applyGridLayout()
    },
    'monitorTask.serialNumber': function (to, from) {
      // ion current
      if (this.selectedSampleOfIonCurrentData === 'All data') {
        if (this.currentChromatogram.startsWith('Base Peak Chromatogram')) {
          this.ionCurrentData = this.basePeakCurrentData
        } else if (this.currentChromatogram.startsWith('Total Ion Chromatogram')) {
          this.ionCurrentData = this.totalIonCurrentData
        } else if (this.currentChromatogram.startsWith('Extract Ion Chromatogram')) {
          this.ionCurrentData = this.extractIonCurrentData
        }
      } else {
        // TODO
      }
      this.ionCurrentDataFilterOptions = this.monitorTask.monitorSamples

      // landmark rt
      if (this.selectedLandmarkOfRt === 'All data') {
        this.selectedLandmarkRtData = this.landmarkRtData
      } else {
        for (let i = 0; i < this.landmarkRtData.length; i++) {
          if (this.landmarkRtData[i].name === this.selectedLandmarkOfRt) {
            this.selectedLandmarkRtData = [this.landmarkRtData[i]]
            break
          }
        }
      }

      // landmark intensity
      if (this.selectedLandmarkOfIntensity === 'All data') {
        this.selectedLandmarkIntensityData = this.landmarkIntensityData
      } else {
        for (let i = 0; i < this.landmarkIntensityData.length; i++) {
          if (this.landmarkIntensityData[i].name === this.selectedLandmarkOfIntensity) {
            this.selectedLandmarkIntensityData = [this.landmarkIntensityData[i]]
            break
          }
        }
      }

      // landmark relative m/z error
      if (this.selectedLandmarkOfRelMzError === 'All data') {
        this.selectedLandmarkRelMzErrorData = this.landmarkRelMzErrorData
      } else {
        for (let i = 0; i < this.landmarkRelMzErrorData.length; i++) {
          if (this.landmarkRelMzErrorData[i].name === this.selectedLandmarkOfRelMzError) {
            this.selectedLandmarkRelMzErrorData = [this.landmarkRelMzErrorData[i]]
            break
          }
        }
      }

      // landmark absolute m/z error
      if (this.selectedLandmarkOfAbsMzError === 'All data') {
        this.selectedLandmarkAbsMzErrorData = this.landmarkAbsMzErrorData
      } else {
        for (let i = 0; i < this.landmarkAbsMzErrorData.length; i++) {
          if (this.landmarkAbsMzErrorData[i].name === this.selectedLandmarkOfAbsMzError) {
            this.selectedLandmarkAbsMzErrorData = [this.landmarkAbsMzErrorData[i]]
            break
          }
        }
      }

      // landmark peak area
      if (this.selectedLandmarkOfPeakArea === 'All data') {
        this.selectedLandmarkPeakAreaData = this.landmarkPeakAreaData
      } else {
        for (let i = 0; i < this.landmarkPeakAreaData.length; i++) {
          if (this.landmarkPeakAreaData[i].name === this.selectedLandmarkOfPeakArea) {
            this.selectedLandmarkPeakAreaData = [this.landmarkPeakAreaData[i]]
            break
          }
        }
      }
    }
  },
  methods: {
    loadMonitorDirTree () {
      this.selectMonitorFileDialogVisible = true
      var setting = {
        data: { simpleData: { enable: true } },
        async: {
          enable: true,
          contentType: 'application/json',
          url: API_BASE + '/tree',
          dataType: 'text',
          autoParam: ['id'],
          dataFilter: function (treeId, parentNode, childNodes) {
            if (!childNodes) return null
            for (var i = 0, l = childNodes.length; i < l; i++) {
              childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.')
            }
            return childNodes
          }
        },
        view: {
          selectedMulti: false // 禁止多选
        }
      }
      this.$nextTick(() => {
        this.zTreeObj = $.fn.zTree.init($('#tree'), setting)
      })
    },
    monitorFileConfirm () {
      var selectedNode = this.zTreeObj.getSelectedNodes()
      this.monitorTask.monitorPath = selectedNode[0].id
      this.selectMonitorFileDialogVisible = false
    },
    start () {
      var self = this
      var queryBody = this.buildQueryBody()

      // resume task
      if (self.monitorTask.taskId !== null) {
        this.axios({
          method: 'post',
          url: API_BASE + `/task/resume/${self.monitorTask.taskId}`,
          data: queryBody
        }).then(successResp => {
          if (successResp.data.code === 200) {
            self.monitorTask.monitoring = true
            self.fetchData()
          }
        }).catch(failResp => {
          console.log(failResp)
        })
      } else {
        // create task
        this.axios({
          method: 'post',
          url: API_BASE + '/task/create',
          data: queryBody
        }).then(successResp => {
          if (successResp.data.code === 200) {
            const taskId = successResp.data.data
            self.monitorTask.taskId = taskId
            self.monitorTask.monitoring = true
            self.fetchData()
          }
        }).catch(failResp => {
          console.log(failResp)
        })
      }
    },
    stop () {
      this.axios({
        method: 'get',
        url: API_BASE + '/task/stop/' + this.monitorTask.taskId
      }).then(successResp => {
        this.monitorTask.monitoring = false
      }).catch(failResp => {
        console.log(failResp)
        this.monitorTask.monitoring = false
      })
    },
    fetchData () {
      var self = this
      // 创建 EventSource 对象，连接到服务器端点
      if (this.eventSource === null) {
        this.eventSource = new EventSource(API_BASE + `/task/data/${this.monitorTask.taskId}`)
      } else {
        this.eventSource.close()
        this.eventSource = new EventSource(API_BASE + `/task/data/${this.monitorTask.taskId}`)
      }

      // 监听update-sample更新事件
      this.eventSource.addEventListener('update-sample', function (event) {
        self.monitorTask.monitorSamples.push(event.data)
      })

      // 监听update-bpc更新事件
      this.eventSource.addEventListener('update-bpc', function (event) {
        const val = JSON.parse(event.data)
        let bpcTrace = {
          name: val.sample,
          mode: 'lines',
          x: val.rt,
          y: val.intensity,
          line: { width: 1 }
        }
        self.basePeakCurrentData.push(bpcTrace)
        self.monitorTask.serialNumber = Math.floor(Math.random() * (10000 - 1)) + 1
      })

      // 监听update-tic更新事件
      this.eventSource.addEventListener('update-tic', function (event) {
        const val = JSON.parse(event.data)
        let ticTrace = {
          name: val.sample,
          x: val.rt,
          y: val.tic,
          line: { width: 1 }
        }
        self.totalIonCurrentData.push(ticTrace)
        self.monitorTask.serialNumber = Math.floor(Math.random() * (10000 - 1)) + 1
      })

      // 监听update-eic更新事件
      this.eventSource.addEventListener('update-eic', function (event) {
        const val = JSON.parse(event.data)
        self.renderExtractIonCurrentPanel(val)
      })

      // 监听update-landmark-data事件
      this.eventSource.addEventListener('update-landmark-data', function (event) {
        const val = JSON.parse(event.data)
        self.renderLandmarkRtPanel(val)
        self.renderLandmarkIntensityPanel(val)
        self.renderLandmarkRelMzErrorPanel(val)
        self.renderLandmarkPeakAreaPanel(val)
      })
    },
    renderBasePeakCurrentPanel (bpcList) {
      this.basePeakCurrentData = []
      for (let i = 0; i < bpcList.length; i++) {
        const val = bpcList[i]
        let bpcTrace = {
          name: val.sample,
          mode: 'lines',
          x: val.rt,
          y: val.intensity,
          line: { width: 1 }
        }
        this.basePeakCurrentData.push(bpcTrace)
      }
      this.monitorTask.serialNumber = Math.floor(Math.random() * (10000 - 1)) + 1
    },
    renderTotalIonCurrentPanel (ticList) {
      this.totalIonCurrentData = []
      for (let i = 0; i < ticList.length; i++) {
        const val = ticList[i]
        let ticTrace = {
          name: val.sample,
          x: val.rt,
          y: val.tic,
          line: { width: 1 }
        }
        this.totalIonCurrentData.push(ticTrace)
      }
      this.monitorTask.serialNumber = Math.floor(Math.random() * (10000 - 1)) + 1
    },
    renderExtractIonCurrentPanel (val) {
      this.extractIonCurrentData = []
      for (let i = 0; i < val.length; i++) {
        let extractIonCurrentTrace = {
          name: val[i].landmark,
          x: val[i].rt,
          y: val[i].intensity,
          mode: 'lines',
          line: { width: 1 },
          marker: { size: 1 },
          connectgaps: false
        }
        this.extractIonCurrentData.push(extractIonCurrentTrace)
      }
      this.monitorTask.serialNumber = Math.floor(Math.random() * (10000 - 1)) + 1
    },
    renderLandmarkRtPanel (val) {
      this.landmarkRtData = []
      for (let i = 0; i < val.length; i++) {
        let option = this.panelOptions.landmarkRt
        let symbol = val[i].sample.map(name => this.isQC(name) ? option.markerShapeOfQC : option.markerShapeOfBlank)
        let size = val[i].sample.map(name => this.isQC(name) ? option.markerSizeOfQC : option.markerSizeOfBlank)
        let color = val[i].sample.map(name => this.isQC(name) ? option.markerColorOfQC : option.markerColorOfBlank)
        let trace = {
          name: val[i].landmark,
          mode: 'lines+markers',
          x: val[i].index,
          y: val[i].rt,
          line: { width: option.lineWidth },
          marker: { symbol: symbol, size: size, color: color },
          text: val[i].sample,
          sampleName: val[i].sample,
          hovertemplate: 'sample: %{text}<br>value: %{y}'
        }
        this.landmarkRtData.push(trace)
      }
      this.monitorTask.serialNumber = Math.floor(Math.random() * (10000 - 1)) + 1
    },
    renderLandmarkIntensityPanel (val) {
      this.landmarkIntensityData = []
      for (let i = 0; i < val.length; i++) {
        let option = this.panelOptions.landmarkIntensity
        let symbol = val[i].sample.map(name => this.isQC(name) ? option.markerShapeOfQC : option.markerShapeOfBlank)
        let size = val[i].sample.map(name => this.isQC(name) ? option.markerSizeOfQC : option.markerSizeOfBlank)
        let color = val[i].sample.map(name => this.isQC(name) ? option.markerColorOfQC : option.markerColorOfBlank)
        let trace = {
          name: val[i].landmark,
          mode: 'lines+markers',
          x: val[i].index,
          y: val[i].intensity,
          line: { width: option.lineWidth },
          marker: { symbol: symbol, size: size, color: color },
          text: val[i].sample,
          sampleName: val[i].sample,
          hovertemplate: 'sample: %{text}<br>value: %{y}'
        }
        this.landmarkIntensityData.push(trace)
        this.monitorTask.serialNumber = Math.floor(Math.random() * (10000 - 1)) + 1
      }
    },
    renderLandmarkRelMzErrorPanel (val) {
      this.landmarkRelMzErrorData = []
      for (let i = 0; i < val.length; i++) {
        let option = this.panelOptions.landmarkRelMzError
        let symbol = val[i].sample.map(name => this.isQC(name) ? option.markerShapeOfQC : option.markerShapeOfBlank)
        let size = val[i].sample.map(name => this.isQC(name) ? option.markerSizeOfQC : option.markerSizeOfBlank)
        let color = val[i].sample.map(name => this.isQC(name) ? option.markerColorOfQC : option.markerColorOfBlank)
        let trace = {
          name: val[i].landmark,
          mode: 'lines+markers',
          x: val[i].index,
          y: val[i].relMzError,
          line: { width: option.lineWidth },
          marker: { symbol: symbol, size: size, color: color },
          text: val[i].sample,
          sampleName: val[i].sample,
          hovertemplate: 'sample: %{text}<br>value: %{y}'
        }
        this.landmarkRelMzErrorData.push(trace)
      }
      this.monitorTask.serialNumber = Math.floor(Math.random() * (10000 - 1)) + 1
    },
    renderLandmarkAbsMzErrorPanel (val) {
      this.landmarkAbsMzErrorData = []
      for (let i = 0; i < val.length; i++) {
        let option = this.panelOptions.landmarkAbsMzError
        let symbol = val[i].sample.map(name => this.isQC(name) ? option.markerShapeOfQC : option.markerShapeOfBlank)
        let size = val[i].sample.map(name => this.isQC(name) ? option.markerSizeOfQC : option.markerSizeOfBlank)
        let color = val[i].sample.map(name => this.isQC(name) ? option.markerColorOfQC : option.markerColorOfBlank)
        let trace = {
          name: val[i].landmark,
          mode: 'lines+markers',
          x: val[i].index,
          y: val[i].absMzError,
          line: { width: option.lineWidth },
          marker: { symbol: symbol, size: size, color: color },
          text: val[i].sample,
          sampleName: val[i].sample,
          hovertemplate: 'sample: %{text}<br>value: %{y}'
        }
        this.landmarkAbsMzErrorData.push(trace)
      }
      this.monitorTask.serialNumber = Math.floor(Math.random() * (10000 - 1)) + 1
    },
    renderLandmarkPeakAreaPanel (val) {
      this.landmarkPeakAreaData = []
      for (let i = 0; i < val.length; i++) {
        let option = this.panelOptions.landmarkPeakArea
        let symbol = val[i].sample.map(name => this.isQC(name) ? option.markerShapeOfQC : option.markerShapeOfBlank)
        let size = val[i].sample.map(name => this.isQC(name) ? option.markerSizeOfQC : option.markerSizeOfBlank)
        let color = val[i].sample.map(name => this.isQC(name) ? option.markerColorOfQC : option.markerColorOfBlank)
        let trace = {
          name: val[i].landmark,
          mode: 'lines+markers',
          x: val[i].index,
          y: val[i].peakArea,
          line: { width: option.lineWidth },
          marker: { symbol: symbol, size: size, color: color },
          text: val[i].sample,
          sampleName: val[i].sample,
          hovertemplate: 'sample: %{text}<br>value: %{y}'
        }
        this.landmarkPeakAreaData.push(trace)
      }
      this.monitorTask.serialNumber = Math.floor(Math.random() * (10000 - 1)) + 1
    },
    refreshData (data, options) {
      for (var i = 0; i < data.length; i++) {
        data[i].line.width = options.lineWidth
        var markerSize = []
        var markerSymbol = []
        var symbolColor = []
        for (var j = 0; j < data[i].sampleName.length; j++) {
          if (data[i].sampleName[j].indexOf('QC') !== -1) {
            markerSize.push(options.markerSizeOfQC)
            markerSymbol.push(options.markerShapeOfQC)
          } else {
            markerSize.push(options.markerSizeOfBlank)
            markerSymbol.push(options.markerShapeOfBlank)
          }
          if (options.markerColorOfQC !== '') {
            if (data[i].sampleName[j].indexOf('QC') !== -1) {
              symbolColor.push(options.markerColorOfQC)
            } else {
              if (options.markerColorOfBlank !== '') {
                symbolColor.push(options.markerColorOfBlank)
              } else {
                symbolColor.push('rgba(0, 0, 0, 0)')
              }
            }
          }
        }
        data[i].marker.size = markerSize
        data[i].marker.symbol = markerSymbol
        if (symbolColor.length > 0) {
          data[i].marker.color = symbolColor
        }
      }
    },
    isQC (name) {
      if (name.indexOf('QC') === -1 && name.indexOf('qc') === -1) {
        return false
      } else {
        return true
      }
    },
    /**
     * 求中位数
     * @param {} data
     */
    median (data) {
      var getSortedArray = function (data) { return data.slice().sort((a, b) => a - b) }
      let sortedArr = getSortedArray(data)
      let inputLength = data.length
      let middleIndex = Math.floor(inputLength / 2)
      let oddLength = inputLength % 2 !== 0
      let median
      if (oddLength) { // if array length is odd -> return element at middleIndex
        median = sortedArr[middleIndex]
      } else {
        median = (sortedArr[middleIndex] + sortedArr[middleIndex - 1]) / 2
      }
      return median
    },
    /**
     * 求标准差
     * @param {} data
     */
    stdev (data) {
      var sum = function (x, y) { return x + y }
      var square = function (x) { return x * x }
      var mean = data.reduce(sum) / data.length
      var deviations = data.map(function (x) { return (x - mean) })
      var stdev = Math.sqrt(deviations.map(square).reduce(sum) / (data.length - 1))
      return stdev
    },
    unitrt (number, unit) {
      number = Number(number)
      var val = number
      switch (unit) {
        case 'min':
          val = number
          break
        case 's':
          val = number / 60
          break
        default:
          val = number
      }
      return val
    },
    unitppm (number, unit) {
      var val = number
      switch (unit) {
        case 'ppm':
          val = number
          break
        default:
          val = number
      }
      return val
    },
    buildQueryBody () {
      var queryBody = {}
      queryBody.rawDataDir = this.monitorTask.monitorPath
      var landmarks = []
      for (var i = 0; i < this.landmarkData.length; i++) {
        landmarks.push({
          name: this.landmarkData[i].name,
          rtVal: this.unitrt(this.landmarkData[i].rt, this.landmarkData[i].rtUnit),
          rtMin: this.unitrt(this.landmarkData[i].rt, this.landmarkData[i].rtUnit) - this.unitrt(this.peakExtractionForm.rt, this.peakExtractionForm.rtUnit),
          rtMax: this.unitrt(this.landmarkData[i].rt, this.landmarkData[i].rtUnit) + this.unitrt(this.peakExtractionForm.rt, this.peakExtractionForm.rtUnit),
          mzVal: Number(this.landmarkData[i].mzRatio),
          mzMin: Number(this.landmarkData[i].mzRatio) - Number(this.landmarkData[i].mzRatio) * this.peakExtractionForm.mzRatio / 1e6,
          mzMax: Number(this.landmarkData[i].mzRatio) + Number(this.landmarkData[i].mzRatio) * this.peakExtractionForm.mzRatio / 1e6
        })
      }
      queryBody.landmarks = landmarks
      return queryBody
    },
    confirmEditLandmark (row) {
      this.axios({
        method: 'put',
        url: API_BASE + '/landmark',
        data: {
          id: row.id,
          name: row.name,
          rt: row.rt + row.rtUnit,
          mz: row.mzRatio,
          polarity: row.polarity
        }
      }).then(successResp => {
        if (successResp.data.code === 200) {
          row.id = successResp.data.data.id
          row.originalName = row.name
          row.originalRt = row.rt
          row.originalRtUnit = row.rtUnit
          row.originalMzRatio = row.mzRatio
          row.originalPolarity = row.polarity
          row.edit = false
        }
      }).catch(failResp => {
        console.log(failResp)
      })
    },
    cancelEditLandmark (row) {
      this.axios({
        method: 'put',
        url: API_BASE + '/landmark',
        data: {
          id: row.id,
          name: row.originalName,
          rt: row.originalRt + row.originalRtUnit,
          mz: row.originalMzRatio,
          polarity: row.originalPolarity
        }
      }).then(successResp => {
        if (successResp.data.code === 200) {
          row.id = successResp.data.data.id
          row.name = row.originalName
          row.rt = row.originalRt
          row.rtUnit = row.originalRtUnit
          row.mzRatio = row.originalMzRatio
          row.polarity = row.originalPolarity
          row.edit = false
        }
      }).catch(failResp => {
        console.log(failResp)
      })
    },
    addLandmark () {
      this.landmarkData.push({
        id: null,
        name: 'landmark',
        rt: 10,
        rtUnit: 'min',
        mzRatio: 2,
        mzRatioUnit: 'ppm',
        polarity: '+',
        originalName: 'landmark',
        originalRt: 10,
        originalRtUnit: 'min',
        originalMzRatio: 2,
        originalPolarity: '+',
        edit: true
      })
    },
    removeLandmark (index) {
      var id = this.landmarkData[index].id
      this.axios({
        method: 'delete',
        url: API_BASE + '/landmark/' + id
      }).then(successResp => {
        if (successResp.data.acknowledged === true) {
          this.landmarkData.splice(index, 1)
        }
      }).catch(failResp => {
        console.log(failResp)
      })
    },
    beforeUpload (file) {
      const isLt1M = file.size / 1024 / 1024 < 1
      if (isLt1M) {
        return true
      }
      this.$message({
        message: 'Please do not upload files larger than 1m in size.',
        type: 'warning'
      })
      return false
    },
    handleSuccess ({ results, header }) {
      this.landmarkData = []
      for (var i = 0; i < results.length; i++) {
        this.landmarkData.push({
          id: null,
          name: results[i][header[0]],
          rt: results[i][header[1]].replace(/[^0-9.]/ig, ''),
          rtUnit: results[i][header[1]].replace(/[^a-zA-Z]/ig, ''),
          mzRatio: results[i][header[2]],
          polarity: results[i][header[3]]
        })
        this.landmarkData.map(v => {
          this.$set(v, 'edit', false)
          v.originalName = v.name
          v.originalRt = v.rt
          v.originalRtUnit = v.rtUnit
          v.originalMzRatio = v.mzRatio
          v.originalPolarity = v.polarity
          return v
        })
      }
      this.uploadLandmarks()
    },
    loadLandmarks () {
      var self = this
      this.axios({
        method: 'get',
        url: API_BASE + '/landmark'
      }).then(successResp => {
        if (successResp.data.code === 200) {
          self.landmarkData = successResp.data.data.map(v => {
            self.$set(v, 'edit', false)
            v.originalName = v.name
            v.originalRtUnit = v.rt.replace(/[^a-zA-Z]/ig, '')
            v.rtUnit = v.rt.replace(/[^a-zA-Z]/ig, '')
            v.originalRt = v.rt.replace(/[^0-9.]/ig, '')
            v.rt = v.rt.replace(/[^0-9.]/ig, '')
            v.mzRatio = v.mz
            v.originalMzRatio = v.mz
            v.originalPolarity = v.polarity
            return v
          })
        }
      }).catch(failResp => {
        console.log(failResp)
      })
    },
    uploadLandmarks () {
      var landmarks = []
      for (var i = 0; i < this.landmarkData.length; i++) {
        landmarks.push({
          id: this.landmarkData[i].id,
          name: this.landmarkData[i].name,
          rt: this.landmarkData[i].rt + this.landmarkData[i].rtUnit,
          mz: this.landmarkData[i].mzRatio,
          polarity: this.landmarkData[i].polarity
        })
      }
      this.axios({
        method: 'put',
        url: API_BASE + '/landmark/upload',
        data: landmarks
      }).then(successResp => {
      }).catch(failResp => {
        console.log(failResp)
      })
    },
    toggleViewMode () {
      if (this.viewMode === 'default') {
        this.viewMode = 'hideSidebar'
      } else if (this.viewMode === 'hideSidebar') {
        this.viewMode = 'hideHeader'
        this.$notify({
          title: 'Tips',
          message: 'Press Esc to exit full screen mode',
          type: 'success'
        })
      }
    },
    resizePanel (i, newH, newW, newHPx, newWPx) {
      if (i === 0) {
        this.ionCurrentLayout.width = (newW * this.gridColWidth) + (newW - 1) * 10 - 20
        this.ionCurrentLayout.height = (newH * this.gridRowHeight) + (newH - 1) * 10 - 40 - 20
      } else if (i === 1) {
        this.landmarkRtLayout.width = (newW * this.gridColWidth) + (newW - 1) * 10 - 20
        this.landmarkRtLayout.height = (newH * this.gridRowHeight) + (newH - 1) * 10 - 40 - 20
      } else if (i === 2) {
        this.landmarkIntensityLayout.width = (newW * this.gridColWidth) + (newW - 1) * 10 - 20
        this.landmarkIntensityLayout.height = (newH * this.gridRowHeight) + (newH - 1) * 10 - 40 - 20
      } else if (i === 3) {
        this.landmarkRelMzErrorLayout.width = (newW * this.gridColWidth) + (newW - 1) * 10 - 20
        this.landmarkRelMzErrorLayout.height = (newH * this.gridRowHeight) + (newH - 1) * 10 - 40 - 20
      } else if (i === 4) {
        this.landmarkAbsMzErrorLayout.width = (newW * this.gridColWidth) + (newW - 1) * 10 - 20
        this.landmarkAbsMzErrorLayout.height = (newH * this.gridRowHeight) + (newH - 1) * 10 - 40 - 20
      } else if (i === 5) {
        this.gridLayout[i].h = newH
        this.landmarkPeakAreaLayout.width = (newW * this.gridColWidth) + (newW - 1) * 10 - 20
        this.landmarkPeakAreaLayout.height = (newH * this.gridRowHeight) + (newH - 1) * 10 - 40 - 20
      }
    },
    /**
     * 更新Panel title
     * @param {main} title 主标题
     * @param {selected} selected 子标题
     */
    updatePanelTitle (title, selected) {
      if (selected === 'All data') {
        if (title.indexOf('(') === -1) {
          return title
        } else {
          return title.substring(0, title.indexOf('('))
        }
      } else {
        if (title.indexOf('(') === -1) {
          return title + ' (' + selected + ')'
        } else {
          var subtitle = title.substring(title.indexOf('(') + 1, title.lastIndexOf(')'))
          return title.replace(subtitle, selected)
        }
      }
    },
    /**
     * 过滤 base peak chromatogram / total ion chromatogram / extract ion chromatogram
     * @param {} name 样本名称
     */
    filterIonCurrentData (name, lastN) {
      this.selectedSampleOfIonCurrentData = name
      this.currentChromatogram = this.updatePanelTitle(this.currentChromatogram, name)
      if (name === 'All data') {
        if (this.currentChromatogram.startsWith('Base Peak Chromatogram')) {
          this.ionCurrentData = this.basePeakCurrentData
        } else if (this.currentChromatogram.startsWith('Total Ion Chromatogram')) {
          this.ionCurrentData = this.totalIonCurrentData
        } else if (this.currentChromatogram.startsWith('Extract Ion Chromatogram')) {
          this.ionCurrentData = this.extractIonCurrentData
        }
        // 筛选 last N 个样本数据进行展示
        if (typeof (lastN) === 'number' && this.ionCurrentData.length >= lastN) {
          this.ionCurrentData = this.ionCurrentData.slice(this.ionCurrentData.length - lastN)
        }
      } else {
        if (this.currentChromatogram.startsWith('Base Peak Chromatogram')) {
          // 因为第一个元素是All data,所以这里的indexOf定位元素索引后要-1
          this.ionCurrentData = [this.basePeakCurrentData[this.ionCurrentDataFilterOptions.indexOf(name) - 1]]
        } else if (this.currentChromatogram.startsWith('Total Ion Chromatogram')) {
          this.ionCurrentData = [this.totalIonCurrentData[this.ionCurrentDataFilterOptions.indexOf(name) - 1]]
        } else if (this.currentChromatogram.startsWith('Extract Ion Chromatogram')) {
          for (var i = 0; i < this.extractIonCurrentData.length; i++) {
            if (this.extractIonCurrentData[i].name === name) {
              this.ionCurrentData = [this.extractIonCurrentData[i]]
              break
            }
          }
        }
      }
    },
    /**
     * 过滤landmark rt
     * @param {} name name of landmark
     */
    filterLandmarkRtData (name, lastN) {
      this.selectedLandmarkOfRt = name
      this.panelTitleOfRT = this.updatePanelTitle(this.panelTitleOfRT, name)
      if (name.startsWith('All')) {
        this.selectedLandmarkRtData = this.landmarkRtData
      } else {
        for (var i = 0; i < this.landmarkRtData.length; i++) {
          if (this.landmarkRtData[i].name === name) {
            this.selectedLandmarkRtData = [this.landmarkRtData[i]]
            break
          }
        }
      }
      // 筛选 last N 个样本数据进行展示
      if (typeof (lastN) === 'number' && this.selectedLandmarkRtData[0].x.length >= lastN) {
        for (var k = 0; k < this.selectedLandmarkRtData.length; k++) {
          this.selectedLandmarkRtData[k].x = this.selectedLandmarkRtData[k].x.slice(this.selectedLandmarkRtData[k].x.length - lastN)
          this.selectedLandmarkRtData[k].y = this.selectedLandmarkRtData[k].y.slice(this.selectedLandmarkRtData[k].y.length - lastN)
        }
      }
      this.renderWLQL(this.selectedLandmarkRtData, 'landmarkRt', this.panelOptions.landmarkRt)
    },
    /**
     * 过滤landmark intensity data
     * @param {} name name of landmark
     */
    filterLandmarkIntensityData (name) {
      this.selectedLandmarkOfIntensity = name
      this.panelTitleOfIntensity = this.updatePanelTitle(this.panelTitleOfIntensity, name)
      if (name.startsWith('All')) {
        this.selectedLandmarkIntensityData = this.landmarkIntensityData
      } else {
        for (var i = 0; i < this.landmarkIntensityData.length; i++) {
          if (this.landmarkIntensityData[i].name === name) {
            this.selectedLandmarkIntensityData = [this.landmarkIntensityData[i]]
            break
          }
        }
      }
      this.renderWLQL(this.selectedLandmarkIntensityData, 'landmarkIntensity', this.panelOptions.landmarkIntensity)
    },
    /**
     * 过滤landmark relative m/z error
     * @param {} name name of landmark
     */
    filterLandmarkRelMzErrorData (name) {
      this.selectedLandmarkOfRelMzError = name
      this.panelTitleOfRelMzError = this.updatePanelTitle(this.panelTitleOfRelMzError, name)
      if (name.startsWith('All')) {
        this.selectedLandmarkRelMzErrorData = this.landmarkRelMzErrorData
      } else {
        for (var i = 0; i < this.landmarkRelMzErrorData.length; i++) {
          if (this.landmarkRelMzErrorData[i].name === name) {
            this.selectedLandmarkRelMzErrorData = [this.landmarkRelMzErrorData[i]]
            break
          }
        }
      }
      this.renderWLQL(this.selectedLandmarkRelMzErrorData, 'landmarkRelMzError', this.panelOptions.landmarkRelMzError)
    },
    /**
     * 过滤landmark absolute m/z error
     * @param {} name name of landmark
     */
    filterLandmarkAbsMzErrorData (name) {
      this.selectedLandmarkOfAbsMzError = name
      this.panelTitleOfAbsMzError = this.updatePanelTitle(this.panelTitleOfAbsMzError, name)
      if (name.startsWith('All')) {
        this.selectedLandmarkAbsMzErrorData = this.landmarkAbsMzErrorData
      } else {
        for (var i = 0; i < this.landmarkAbsMzErrorData.length; i++) {
          if (this.landmarkAbsMzErrorData[i].name === name) {
            this.selectedLandmarkAbsMzErrorData = [this.landmarkAbsMzErrorData[i]]
            break
          }
        }
      }
      this.renderWLQL(this.selectedLandmarkAbsMzErrorData, 'landmarkAbsMzError', this.panelOptions.landmarkAbsMzError)
    },
    /**
     * 过滤landmark peak area
     * @param {} name name of landmark
     */
    filterLandmarkPeakAreaData (name) {
      this.selectedLandmarkOfPeakArea = name
      this.panelTitleOfPeakArea = this.updatePanelTitle(this.panelTitleOfPeakArea, name)
      if (name.startsWith('All')) {
        this.selectedLandmarkPeakAreaData = this.landmarkPeakAreaData
      } else {
        for (var i = 0; i < this.landmarkPeakAreaData.length; i++) {
          if (this.landmarkPeakAreaData[i].name === name) {
            this.selectedLandmarkPeakAreaData = [this.landmarkPeakAreaData[i]]
            break
          }
        }
      }
      this.renderWLQL(this.selectedLandmarkPeakAreaData, 'landmarkPeakArea', this.panelOptions.landmarkPeakArea)
    },
    /**
     * 全局过滤 last n 个样本数据进行展示
     */
    async filterLastNSamples (option) {
      this.selectedLastNSamples = option
      await this.updateLandmarks()
      // await this.updateIonCurrent()
      // this.filterIonCurrentData(this.selectedSampleOfIonCurrentData, option.value)
      this.filterLandmarkRtData(this.selectedLandmarkOfRt, option.value)
      this.filterLandmarkIntensityData(this.selectedLandmarkOfIntensity, option.value)
      this.filterLandmarkAbsMzErrorData(this.selectedLandmarkOfAbsMzError, option.value)
      this.filterLandmarkRelMzErrorData(this.selectedLandmarkOfRelMzError, option.value)
      this.filterLandmarkPeakAreaData(this.selectedLandmarkOfPeakArea, option.value)
    },
    filterLandmarks (name) {
      this.selectedLandmark = name
      this.filterLandmarkRtData(name)
      this.filterLandmarkIntensityData(name)
      this.filterLandmarkRelMzErrorData(name)
      this.filterLandmarkAbsMzErrorData(name)
      this.filterLandmarkPeakAreaData(name)
    },
    /**
     * 所有Panel下拉选项的总控
     * @param {*} command 下拉选项：view, edit, switch
     */
    handleDropdownOfPanel (command) {
      if (command === 'switchIonCurrent1') {
        if (this.currentChromatogram.startsWith('Base Peak Chromatogram')) {
          if (this.switchChromatogram1 === 'Total Ion Chromatogram') {
            this.ionCurrentData = this.totalIonCurrentData
            this.currentChromatogram = 'Total Ion Chromatogram'
            this.switchChromatogram1 = 'Base Peak Chromatogram'
          } else if (this.switchChromatogram1 === 'Extract Ion Chromatogram') {
            this.ionCurrentData = this.extractIonCurrentData
            this.currentChromatogram = 'Extract Ion Chromatogram'
            this.switchChromatogram1 = 'Base Peak Chromatogram'
          }
        } else if (this.currentChromatogram.startsWith('Total Ion Chromatogram')) {
          if (this.switchChromatogram1 === 'Base Peak Chromatogram') {
            this.ionCurrentData = this.basePeakCurrentData
            this.currentChromatogram = 'Base Peak Chromatogram'
            this.switchChromatogram1 = 'Total Ion Chromatogram'
          } else if (this.switchChromatogram1 === 'Extract Ion Chromatogram') {
            this.ionCurrentData = this.extractIonCurrentData
            this.currentChromatogram = 'Extract Ion Chromatogram'
            this.switchChromatogram1 = 'Total Ion Chromatogram'
          }
        } else if (this.currentChromatogram.startsWith('Extract Ion Chromatogram')) {
          if (this.switchChromatogram1 === 'Base Peak Chromatogram') {
            this.ionCurrentData = this.basePeakCurrentData
            this.currentChromatogram = 'Base Peak Chromatogram'
            this.switchChromatogram1 = 'Extract Ion Chromatogram'
          } else if (this.switchChromatogram1 === 'Total Ion Chromatogram') {
            this.ionCurrentData = this.totalIonCurrentData
            this.currentChromatogram = 'Total Ion Chromatogram'
            this.switchChromatogram1 = 'Extract Ion Chromatogram'
          }
        }
      } else if (command === 'switchIonCurrent2') {
        if (this.currentChromatogram.startsWith('Base Peak Chromatogram')) {
          if (this.switchChromatogram2 === 'Total Ion Chromatogram') {
            this.ionCurrentData = this.totalIonCurrentData
            this.currentChromatogram = 'Total Ion Chromatogram'
            this.switchChromatogram2 = 'Base Peak Chromatogram'
          } else if (this.switchChromatogram2 === 'Extract Ion Chromatogram') {
            this.ionCurrentData = this.extractIonCurrentData
            this.currentChromatogram = 'Extract Ion Chromatogram'
            this.switchChromatogram2 = 'Base Peak Chromatogram'
          }
        } else if (this.currentChromatogram.startsWith('Total Ion Chromatogram')) {
          if (this.switchChromatogram2 === 'Base Peak Chromatogram') {
            this.ionCurrentData = this.basePeakCurrentData
            this.currentChromatogram = 'Base Peak Chromatogram'
            this.switchChromatogram2 = 'Total Ion Chromatogram'
          } else if (this.switchChromatogram2 === 'Extract Ion Chromatogram') {
            this.ionCurrentData = this.extractIonCurrentData
            this.currentChromatogram = 'Extract Ion Chromatogram'
            this.switchChromatogram2 = 'Total Ion Chromatogram'
          }
        } else if (this.currentChromatogram.startsWith('Extract Ion Chromatogram')) {
          if (this.switchChromatogram2 === 'Base Peak Chromatogram') {
            this.ionCurrentData = this.basePeakCurrentData
            this.currentChromatogram = 'Base Peak Chromatogram'
            this.switchChromatogram2 = 'Extract Ion Chromatogram'
          } else if (this.switchChromatogram2 === 'Total Ion Chromatogram') {
            this.ionCurrentData = this.totalIonCurrentData
            this.currentChromatogram = 'Total Ion Chromatogram'
            this.switchChromatogram2 = 'Extract Ion Chromatogram'
          }
        }
      } else if (command === 'viewIonCurrent') {
        this.detailPanelData = this.ionCurrentData
        this.detailPanelTitle = this.currentChromatogram
        if (this.currentChromatogram.startsWith('Base Peak Chromatogram')) {
          this.router = { path: 'detail', type: 'basePeakIonCurrent' }
        } else if (this.currentChromatogram.startsWith('Total Ion Chromatogram')) {
          this.router = { path: 'detail', type: 'totalIonCurrent' }
        } else if (this.currentChromatogram.startsWith('Extract Ion Chromatogram')) {
          this.router = { path: 'detail', type: 'extractIonCurrent' }
        }
      } else if (command === 'viewLandmarkRt') {
        this.router = { path: 'detail', type: 'landmarkRt' }
        this.detailPanelData = this.selectedLandmarkRtData
        this.detailPanelTitle = this.panelTitleOfRT
      } else if (command === 'viewLandmarkIntensity') {
        this.router = { path: 'detail', type: 'landmarkIntensity' }
        this.detailPanelData = this.selectedLandmarkIntensityData
        this.detailPanelTitle = this.panelTitleOfIntensity
      } else if (command === 'viewLandmarkRelMzError') {
        this.router = { path: 'detail', type: 'landmarkRelMzError' }
        this.detailPanelData = this.selectedLandmarkRelMzErrorData
        this.detailPanelTitle = this.panelTitleOfRelMzError
      } else if (command === 'viewLandmarkAbsMzError') {
        this.router = { path: 'detail', type: 'landmarkAbsMzError' }
        this.detailPanelData = this.selectedLandmarkAbsMzErrorData
        this.detailPanelTitle = this.panelTitleOfAbsMzError
      } else if (command === 'viewLandmarkPeakArea') {
        this.router = { path: 'detail', type: 'landmarkPeakArea' }
        this.detailPanelData = this.selectedLandmarkPeakAreaData
        this.detailPanelTitle = this.panelTitleOfPeakArea
      } else if (command === 'editLandmarkRt') {
        this.router = { path: 'editor', type: 'landmarkRt' }
        this.editorPanelData = this.selectedLandmarkRtData
        this.editorPanelTitle = this.panelTitleOfRT
        this.editorPanelOptions = this.panelOptions.landmarkRt
        this.renderWLQL(this.editorPanelData, 'landmarkRt', this.editorPanelOptions)
      } else if (command === 'editLandmarkIntensity') {
        this.router = { path: 'editor', type: 'landmarkIntensity' }
        this.editorPanelData = this.selectedLandmarkIntensityData
        this.editorPanelTitle = this.panelTitleOfIntensity
        this.editorPanelOptions = this.panelOptions.landmarkIntensity
        this.renderWLQL(this.editorPanelData, 'landmarkIntensity', this.editorPanelOptions)
      } else if (command === 'editLandmarkRelMzError') {
        this.router = { path: 'editor', type: 'landmarkRelMzError' }
        this.editorPanelData = this.selectedLandmarkRelMzErrorData
        this.editorPanelTitle = this.panelTitleOfRelMzError
        this.editorPanelOptions = this.panelOptions.landmarkRelMzError
        this.renderWLQL(this.editorPanelData, 'landmarkRelMzError', this.editorPanelOptions)
      } else if (command === 'editLandmarkAbsMzError') {
        this.router = { path: 'editor', type: 'landmarkAbsMzError' }
        this.editorPanelData = this.selectedLandmarkAbsMzErrorData
        this.editorPanelTitle = this.panelTitleOfAbsMzError
        this.editorPanelOptions = this.panelOptions.landmarkAbsMzError
        this.renderWLQL(this.editorPanelData, 'landmarkAbsMzError', this.editorPanelOptions)
      } else if (command === 'editLandmarkPeakArea') {
        this.router = { path: 'editor', type: 'landmarkPeakArea' }
        this.editorPanelData = this.selectedLandmarkPeakAreaData
        this.editorPanelTitle = this.panelTitleOfPeakArea
        this.editorPanelOptions = this.panelOptions.landmarkPeakArea
        this.renderWLQL(this.editorPanelData, 'landmarkPeakArea', this.editorPanelOptions)
      }
    },
    handleDropdownOfDetailPanel (command) {
      /*
      if (command === 'editLandmarkRt') {
        this.$router.push({path: 'edit', query: { type: 'landmarkRt' }})
      } else if (command === 'editLandmarkIntensity') {
        this.$router.push({path: 'edit', query: { type: 'landmarkIntensity' }})
      } else if (command === 'editLandmarkRelMzError') {
        this.$router.push({path: 'edit', query: { type: 'landmarkRelMzError' }})
      } else if (command === 'editLandmarkAbsMzError') {
        this.$router.push({path: 'edit', query: { type: 'landmarkAbsMzError' }})
      } else if (command === 'editLandmarkPeakArea') {
        this.$router.push({path: 'edit', query: { type: 'landmarkPeakArea' }})
      }
      */
    },
    handleFilterDetailPanelData (name) {
      this.detailPanelTitle = this.updatePanelTitle(this.detailPanelTitle, name)
      if (this.router.type === 'basePeakIonCurrent' || this.router.type === 'totalIonCurrent' || this.router.type === 'extractIonCurrent') {
        this.filterIonCurrentData(name)
        this.detailPanelData = this.ionCurrentData
      } else if (this.router.type === 'landmarkRt') {
        this.filterLandmarkRtData(name)
        this.detailPanelData = this.selectedLandmarkRtData
      } else if (this.router.type === 'landmarkIntensity') {
        this.filterLandmarkIntensityData(name)
        this.detailPanelData = this.selectedLandmarkIntensityData
      } else if (this.router.type === 'landmarkRelMzError') {
        this.filterLandmarkRelMzErrorData(name)
        this.detailPanelData = this.selectedLandmarkRelMzErrorData
      } else if (this.router.type === 'landmarkAbsMzError') {
        this.filterLandmarkAbsMzErrorData(name)
        this.detailPanelData = this.selectedLandmarkAbsMzErrorData
      } else if (this.router.type === 'landmarkPeakArea') {
        this.filterLandmarkPeakAreaData(name)
        this.detailPanelData = this.selectedLandmarkPeakAreaData
      }
    },
    /**
     * panel 编辑器中的数据过滤功能
     * @param {*} name
     */
    handleFilterEditorPanelData (name) {
      this.editorPanelTitle = this.updatePanelTitle(this.editorPanelTitle, name)
      if (this.router.type === 'basePeakIonCurrent' || this.router.type === 'totalIonCurrent' || this.router.type === 'extractIonCurrent') {
        this.filterIonCurrentData(name)
        this.editorPanelData = this.ionCurrentData
      } else if (this.router.type === 'landmarkRt') {
        this.filterLandmarkRtData(name)
        this.editorPanelData = this.selectedLandmarkRtData
      } else if (this.router.type === 'landmarkIntensity') {
        this.filterLandmarkIntensityData(name)
        this.editorPanelData = this.selectedLandmarkIntensityData
      } else if (this.router.type === 'landmarkRelMzError') {
        this.filterLandmarkRelMzErrorData(name)
        this.editorPanelData = this.selectedLandmarkRelMzErrorData
      } else if (this.router.type === 'landmarkAbsMzError') {
        this.filterLandmarkAbsMzErrorData(name)
        this.editorPanelData = this.selectedLandmarkAbsMzErrorData
      } else if (this.router.type === 'landmarkPeakArea') {
        this.filterLandmarkPeakAreaData(name)
        this.editorPanelData = this.selectedLandmarkPeakAreaData
      }
      this.renderWLQL(this.editorPanelData, this.router.type, this.editorPanelOptions)
    },
    /**
     * 编辑Panel，根据 Panel Options 更新 Panel Data 和 Panel Layout
     */
    editPanel () {
      this.refreshData(this.editorPanelData, this.editorPanelOptions)
      if (this.router.type === 'landmarkRt') {
        this.refreshData(this.landmarkRtData, this.editorPanelOptions)
      } else if (this.router.type === 'landmarkIntensity') {
        this.refreshData(this.landmarkIntensityData, this.editorPanelOptions)
      } else if (this.router.type === 'landmarkRelMzError') {
        this.refreshData(this.landmarkRelMzErrorData, this.editorPanelOptions)
      } else if (this.router.type === 'landmarkAbsMzError') {
        this.refreshData(this.landmarkAbsMzErrorData, this.editorPanelOptions)
      } else if (this.router.type === 'landmarkPeakArea') {
        this.refreshData(this.landmarkPeakAreaData, this.editorPanelOptions)
      }
    },
    renderWLQL (data, type, options) {
      if (data.length === 1) {
        // show uql/bql/uwl/bwl
        var wlqlShapes = []
        var median = this.median(data[0].y)
        var stdev = this.stdev(data[0].y)
        var uql, bql, uwl, bwl
        if (type === 'landmarkRt' || type === 'landmarkRelMzError' || type === 'landmarkAbsMzError') {
          uql = median + options.UQL
          bql = median + options.BQL
          uwl = median + options.UWL
          bwl = median + options.BWL
        } else if (type === 'landmarkIntensity' || type === 'landmarkPeakArea') {
          uql = median + options.UQL * stdev
          bql = median + options.BQL * stdev
          uwl = median + options.UWL * stdev
          bwl = median + options.BWL * stdev
        }
        if (options.showQL) {
          wlqlShapes.push({
            type: 'line',
            xref: 'paper',
            x0: 0,
            y0: uql,
            x1: 1,
            y1: uql,
            line: {
              color: options.lineColorOfQL,
              width: options.lineWidthOfQL
            }
          })
          wlqlShapes.push({
            type: 'line',
            xref: 'paper',
            x0: 0,
            y0: bql,
            x1: 1,
            y1: bql,
            line: {
              color: options.lineColorOfQL,
              width: options.lineWidthOfQL
            }
          })
        }
        if (options.showWL) {
          wlqlShapes.push({
            type: 'line',
            xref: 'paper',
            x0: 0,
            y0: uwl,
            x1: 1,
            y1: uwl,
            line: {
              color: options.lineColorOfWL,
              width: options.lineWidthOfWL
            }
          })
          wlqlShapes.push({
            type: 'line',
            xref: 'paper',
            x0: 0,
            y0: bwl,
            x1: 1,
            y1: bwl,
            line: {
              color: options.lineColorOfWL,
              width: options.lineWidthOfWL
            }
          })
        }
        // update layout
        if (type === 'landmarkRt') {
          this.landmarkRtLayout.shapes = wlqlShapes
          this.editorPanelLayout.shapes = wlqlShapes
        } else if (type === 'landmarkIntensity') {
          this.landmarkIntensityLayout.shapes = wlqlShapes
          this.editorPanelLayout.shapes = wlqlShapes
        } else if (type === 'landmarkRelMzError') {
          this.landmarkRelMzErrorLayout.shapes = wlqlShapes
          this.editorPanelLayout.shapes = wlqlShapes
        } else if (type === 'landmarkAbsMzError') {
          this.landmarkAbsMzErrorLayout.shapes = wlqlShapes
          this.editorPanelLayout.shapes = wlqlShapes
        } else if (type === 'landmarkPeakArea') {
          this.landmarkPeakAreaLayout.shapes = wlqlShapes
          this.editorPanelLayout.shapes = wlqlShapes
        }
      } else {
        this.editorPanelLayout.shapes = []
        if (type === 'landmarkRt') {
          this.landmarkRtLayout.shapes = []
        } else if (type === 'landmarkIntensity') {
          this.landmarkIntensityLayout.shapes = []
        } else if (type === 'landmarkRelMzError') {
          this.landmarkRelMzErrorLayout.shapes = []
        } else if (type === 'landmarkAbsMzError') {
          this.landmarkAbsMzErrorLayout.shapes = []
        } else if (type === 'landmarkPeakArea') {
          this.landmarkPeakAreaLayout.shapes = []
        }
      }
    },
    toggleCollapseEditArea () {
      this.collapseEditArea = !this.collapseEditArea
      if (this.collapseEditArea) {
        if (this.viewMode === 'default') {
          this.editorPanelLayout.width = (document.body.clientWidth - 150 - 20)
        } else if (this.viewMode === 'hideSidebar' || this.viewMode === 'hideHeader') {
          this.editorPanelLayout.width = (document.body.clientWidth - 20)
        }
      } else {
        if (this.viewMode === 'default') {
          this.editorPanelLayout.width = (document.body.clientWidth - 150 - 20 - 300)
        } else if (this.viewMode === 'hideSidebar' || this.viewMode === 'hideHeader') {
          this.editorPanelLayout.width = (document.body.clientWidth - 20 - 300)
        }
      }
    },
    goBack () {
      this.router.path = 'overview'
    },
    /**
     * 点击左侧样本列表
     * @param {*} name 样本名称
     */
    onSelectedSample (name) {
      this.filterIonCurrentData(name)
    },
    /**
     * 双击左侧样本列表，会选中所有样本
     * @param {} name 样本名称
     */
    onDBClickSample (name) {
      this.filterIonCurrentData('All data')
    },
    onSaveProject () {
      this.saveGridLayout()
      this.savePanelOptions('default')
      this.saveProject()
      this.saveProjectDialogVisible = false
    },
    onOpenRecentProjects () {
      this.openProjectDrawerVisible = true
      this.getAllProjects()
    },
    getAllProjects () {
      var self = this
      this.axios({
        method: 'get',
        url: API_BASE + '/projects'
      }).then(successResp => {
        if (successResp.data.code === 200) {
          self.projects = successResp.data.data
        }
      }).catch(failResp => {
        console.log(failResp)
      })
    },
    loadProject (id) {
      var self = this
      this.axios({
        method: 'get',
        url: API_BASE + `/project/${id}`
      }).then(successResp => {
        if (successResp.data.code === 200) {
          const data = successResp.data.data
          self.monitorTask.monitoring = false
          self.monitorTask.taskId = data.taskId
          self.monitorTask.monitorPath = data.monitorPath
          self.monitorTask.monitorSamples = data.monitorSamples

          const bpcList = data.basePeakCurrentList
          self.renderBasePeakCurrentPanel(bpcList)

          const ticList = data.totalIonCurrentList
          self.renderTotalIonCurrentPanel(ticList)

          const eicList = data.extractIonCurrentList
          self.renderExtractIonCurrentPanel(eicList)

          const landmarkDataList = data.landmarkDataList
          self.renderLandmarkRtPanel(landmarkDataList)
          self.renderLandmarkIntensityPanel(landmarkDataList)
          self.renderLandmarkRelMzErrorPanel(landmarkDataList)
          self.renderLandmarkAbsMzErrorPanel(landmarkDataList)
          self.renderLandmarkPeakAreaPanel(landmarkDataList)
        } else {
          this.$notify({
            title: 'Project load failure, please try again later.',
            type: 'warning'
          })
        }
      }).catch(failResp => {
        console.log(failResp)
        this.$notify({
          title: 'Project load failure, please try again later.',
          type: 'warning'
        })
      })

      this.openProjectDrawerVisible = false
    },
    deleteProject (row) {
      var self = this
      this.axios({
        method: 'delete',
        url: API_BASE + `/project/${row.id}`
      }).then(successResp => {
        if (successResp.data.code === 200) {
          self.projects.splice(row.$index, 1)
        }
      }).catch(failResp => {
        console.log(failResp)
      })
    },
    /**
     * 加载布局
     */
    loadGridLayout () {
      var self = this
      this.axios({
        method: 'get',
        url: API_BASE + '/gridlayout/0001'
      }).then(successResp => {
        if (successResp.data.code === 200) {
          self.gridLayout = JSON.parse(successResp.data.data)
          self.applyGridLayout()
        }
      }).catch(failResp => {
        console.log(failResp)
      })
    },
    saveGridLayout () {
      this.axios({
        method: 'put',
        url: API_BASE + '/gridlayout',
        data: {
          id: '0001',
          name: this.project.name,
          layout: JSON.stringify(this.gridLayout)
        }
      }).then(successResp => {
        if (successResp.data.code !== 200) {
          this.$notify({
            title: 'Project saving failure, please try again later.',
            type: 'warning'
          })
        }
      }).catch(failResp => {
        console.log(failResp)
        this.$notify({
          title: 'Project saving failure, please try again later.',
          type: 'warning'
        })
      })
    },
    /**
     * 加载panel设置
     */
    loadPanelOptions (id) {
      var self = this
      this.axios({
        method: 'get',
        url: API_BASE + `/paneloptions/${id}`
      }).then(successResp => {
        if (successResp.data.code === 200) {
          const options = JSON.parse(successResp.data.data.options)
          self.panelOptions.landmarkRt = options.landmarkRt
          self.panelOptions.landmarkIntensity = options.landmarkIntensity
          self.panelOptions.landmarkRelMzError = options.landmarkRelMzError
          self.panelOptions.landmarkAbsMzError = options.landmarkAbsMzError
          self.panelOptions.landmarkPeakArea = options.landmarkPeakArea
        }
      }).catch(failResp => {
        console.log(failResp)
      })
    },
    savePanelOptions (id) {
      this.axios({
        method: 'put',
        url: API_BASE + `/paneloptions`,
        data: {
          id: id,
          options: JSON.stringify(this.panelOptions)
        }
      }).then(successResp => {
        if (successResp.data.code !== 200) {
          this.$notify({
            title: 'Project saving failure, please try again later.',
            type: 'warning'
          })
        }
      }).catch(failResp => {
        console.log(failResp)
        this.$notify({
          title: 'Project saving failure, please try again later.',
          type: 'warning'
        })
      })
    },
    saveProject () {
      this.axios({
        method: 'put',
        url: API_BASE + `/project/${this.monitorTask.taskId}`,
        data: {
          name: this.project.name
        }
      }).then(successResp => {
        if (successResp.data.code !== 200) {
          this.$notify({
            title: 'Project saving failure, please try again later.',
            type: 'warning'
          })
        }
      }).catch(failResp => {
        console.log(failResp)
        this.$notify({
          title: 'Project saving failure, please try again later.',
          type: 'warning'
        })
      })
    },
    /**
     * 根据grid layout的设置调整panel的width和height
     */
    applyGridLayout () {
      this.gridRowHeight = (this.layout.main.height - 390) / 40
      this.gridColWidth = (this.layout.main.width - 470) / 48
      this.ionCurrentLayout.width = (this.gridLayout[0].w * this.gridColWidth) + (this.gridLayout[0].w - 1) * 10 - 20
      this.ionCurrentLayout.height = (this.gridLayout[0].h * this.gridRowHeight) + (this.gridLayout[0].h - 1) * 10 - 40 - 20
      this.landmarkRtLayout.width = (this.gridLayout[1].w * this.gridColWidth) + (this.gridLayout[1].w - 1) * 10 - 20
      this.landmarkRtLayout.height = (this.gridLayout[1].h * this.gridRowHeight) + (this.gridLayout[1].h - 1) * 10 - 40 - 20
      this.landmarkIntensityLayout.width = (this.gridLayout[2].w * this.gridColWidth) + (this.gridLayout[2].w - 1) * 10 - 20
      this.landmarkIntensityLayout.height = (this.gridLayout[2].h * this.gridRowHeight) + (this.gridLayout[2].h - 1) * 10 - 40 - 20
      this.landmarkRelMzErrorLayout.width = (this.gridLayout[3].w * this.gridColWidth) + (this.gridLayout[3].w - 1) * 10 - 20
      this.landmarkRelMzErrorLayout.height = (this.gridLayout[3].h * this.gridRowHeight) + (this.gridLayout[3].h - 1) * 10 - 40 - 20
      this.landmarkAbsMzErrorLayout.width = (this.gridLayout[4].w * this.gridColWidth) + (this.gridLayout[4].w - 1) * 10 - 20
      this.landmarkAbsMzErrorLayout.height = (this.gridLayout[4].h * this.gridRowHeight) + (this.gridLayout[4].h - 1) * 10 - 40 - 20
      this.landmarkPeakAreaLayout.width = (this.gridLayout[5].w * this.gridColWidth) + (this.gridLayout[5].w - 1) * 10 - 20
      this.landmarkPeakAreaLayout.height = (this.gridLayout[5].h * this.gridRowHeight) + (this.gridLayout[5].h - 1) * 10 - 40 - 20
    },
    captureScreenshot () {
      html2canvas(document.body, {
        scrollX: 0,
        scrollY: 0,
        windowWidth: document.documentElement.scrollWidth,
        windowHeight: document.documentElement.scrollHeight,
        scale: 3
      }).then(canvas => {
        // 将画布内容导出为 PNG 格式的数据链接
        const screenshotDataUrl = canvas.toDataURL()
        // 创建一个下载链接并设置相关属性
        const downloadLink = document.createElement('a')
        downloadLink.href = screenshotDataUrl
        // 设置下载文件的名称
        downloadLink.download = 'screenshot.png'
        // 模拟点击下载链接，执行下载操作
        downloadLink.click()
      })
    },
    projectRowClassName ({row, rowIndex}) {
      if (row.status === 'running') {
        return 'project-running-row'
      }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
::-webkit-scrollbar {
  width: 4px;
  height:6px;
}

::-webkit-scrollbar-thumb {
  border-radius: 3px;
  background:#949DAE;
}

.custom-dilaog .el-dialog__header {
  border-bottom: 1px solid #e5e5e5;
  padding: 15px;
}

.custom-dilaog .el-dialog__title {
  font-size: 16px;
}

.custom-dilaog .el-dialog__footer {
  border-top: 1px solid #e5e5e5;
  padding: 15px;
}

.chartContainer {
  background: #fff;
  padding: 10px 20px;
}

.el-dialog__body {
  padding: 15px !important;
  overflow: auto;
}

fieldset {
  border: 1px solid rgb(222, 222, 222);
}

.el-form-item {
  margin-bottom: 15px;
}

.el-table .el-table__cell {
  padding: 8px 0px;
}

.el-popper[x-placement^=bottom] {
    margin-top: 0px;
}

.iconfont {
  font-size: 12px;
}

.sample-list-container {
  padding: 0px 10px 5px 5px;
  border-right: 1px solid rgb(216, 222, 228);
  overflow-y: scroll;
  overflow-x: hidden;
  box-sizing: border-box;
  max-height: calc(100vh - 50px);
}

.sample-list-item {
  margin-top: 5px;
  margin-bottom: 5px;
  padding: 3px 5px;
  border-radius: 4px;
  font-size: .675rem;
  color: white;
  border: 2px solid #0060df;
  background-color: #0060df;
}

.sample-list-item:hover {
  border: 2px solid #0250bb;
  background-color: #0250bb;
}

.sample-list-item-qc {
  margin-top: 5px;
  margin-bottom: 5px;
  padding: 3px 5px;
  border-radius: 4px;
  font-size: .675rem;
  color: white;
  /*border: 2px solid #e22850;
  background-color: #e22850;*/
  border: 2px solid #f5af41;
  background-color: #f5af41;
}

.sample-list-item-qc:hover {
  border: 2px solid #d59229;
  background-color: #d59229;
}

.primary-button {
  padding: 9px 15px;
  font-size: 12px;
  border-radius: 3px;
  color: #FFF;
  background-color: #409EFF;
  border-color: #409EFF;
}

.css-toolbar-button {
  position: relative;
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  height: 32px;
  padding: 0px 8px;
  border-radius: 2px;
  line-height: 30px;
  font-weight: 500;
  border: 1px solid rgba(36, 41, 46, 0.12);
  white-space: nowrap;
  transition: background 250ms cubic-bezier(0.4, 0, 0.2, 1) 0ms, box-shadow 250ms cubic-bezier(0.4, 0, 0.2, 1) 0ms, border-color 250ms cubic-bezier(0.4, 0, 0.2, 1) 0ms, color 250ms cubic-bezier(0.4, 0, 0.2, 1) 0ms;
  color: rgba(36, 41, 46, 0.75);
  background-color: rgb(255, 255, 255);
}

.monitoring {
  /*-moz-animation: ;background-color: rgb(92, 192, 92);
  border-color: rgb(92, 192, 92);*/
  animation: animated-border 1.5s infinite;
}

.monitoring:hover {
  background-color: rgba(92, 192, 92, 0.8);
}

@keyframes animated-border {
  0% {
    border-radius: 5px;
    box-shadow: 0 0 0 0 rgba(92, 192, 92, 0.8);
  }
  100% {
    box-shadow: 0 0 0 8px rgba(185, 231, 105, 0.2);
  }
}

.toolbar-button {
  padding: 5px 8px;
  border: 1px solid rgba(36, 41, 46, 0.12);
  background: rgb(255, 255, 255);
  color: rgba(36, 41, 46, 0.75);
  height: 32px;
  display: flex;
  align-items: center;
  display: flex !important;
}

.toolbar-button:hover {
  color: rgba(36, 41, 46, 1) !important;
  background-color: rgb(244, 245, 245) !important;
  border: 1px solid rgb(219, 220, 221) !important;
}

.plus-button {
  width:100%;
  border: 2px solid #E2E8F0;;
  border-radius: 5px;
  background-color:white;
  color:#bbbbbb;
  font-size: 16px;
}

.plus-button:hover {
  border: 2px solid #4dadff;
  color: #298cff;
}

.el-select-dropdown__item {
    font-size: 12px !important;
    padding: 0 15px;
    color: #606266;
    height: 24px !important;
    line-height: 24px !important;
}

.el-select-dropdown__item.hover, .el-select-dropdown__item:hover {
    background-color: #1e90ff;
    color: white;
}

.el-select-dropdown__list {
  padding: 0px;
}

.el-popper[x-placement^=bottom] .popper__arrow {
  display: none;
}

.vue-swatches__trigger, .vue-swatches--is-empty {
  width: 40px !important;
  height: 20px !important;
  border-radius: 5px !important;
}

.vue-swatches__swatch {
  width: 20px !important;
  height: 20px !important;
  border-radius: 4px !important;
  margin-right: 4px !important;
  margin-bottom: 4px !important;
}

.vue-swatches__wrapper {
  width: 100px !important;
  padding-left: 5px !important;
  padding-bottom: 5px !important;
}

.vue-swatches__container {
  padding: 0px 5px !important;
}

.vue-swatches__fallback__button {
  padding: 2px 6px !important;
}

.vue-swatches__fallback__input {
  padding-top: 2px !important;
  padding-bottom: 2px !important;
}

.vue-swatches__fallback__wrapper {
  margin-left: 5px !important;
  width: 100% !important;
  width: 100px !important;
}

.el-switch__core {
  height: 16px;
}

.el-switch__core:after {
  width: 12px;
  height: 12px;
}

.el-switch.is-checked .el-switch__core::after {
  margin-left: -14px;
}

.el-drawer__header {
  color: rgb(36, 41, 46) !important;
  font-size: 18px;
  margin-bottom: 16px !important;
}

.el-drawer__body {
  padding-left: 20px !important;
  padding-right: 20px !important;
}

#table-projects {
  border: 2px solid #0f84af;
}

#table-projects > div.el-table {
  border: none !important;
}

#table-projects table thead tr > th {
  background: #397093 !important;
  color: white !important;
}

#table-projects table tr > td  {
  background: #b0e0e6 !important;
  color: black !important;
  padding: 4px 0;
}

#table-projects tr.el-table__row.project-running-row > td {
  background-color: #6a5acd !important;
  color: white !important;
  padding: 4px 0;
}

.simple-btn {
  color: rgba(36, 41, 46, 0.75) !important;
  font-weight: 600 !important;
  font-size: 13px !important;
}

.el-icon-folder-opened {
  color: rgba(36, 41, 46, 0.75) !important;
  font-weight: 600 !important;
}

.el-input.is-active .el-input__inner, .el-input__inner:focus {
  border-color: #3871dc !important;
  border-width: 2px !important;
}

button.primary-btn.el-button.el-button--primary {
  background-color: #3871dc !important;
  border-color: #3871dc !important;
}
.el-button.primary-btn.el-button--primary:hover {
  background-color: #2c5ab0 !important;
  border-color: #2c5ab0 !important;
}

.monitor-dialog .el-dialog__body {
  max-height: 400px !important;
}
</style>

<style scoped>
.el-dropdown-menu {
  padding: 0 0;
  margin: 0 0;
  border: 1px solid #24292e1f;
  box-shadow: 0 13px 20px 1px #181a1b2e;
}
.el-dropdown-menu__item {
  padding: 0px 10px;
  line-height: 30px;
}

.panelToolsBar {
  height: 40px;
  background-color: #fff;
  text-align: center;
  line-height: 40px;
}

.panelToolsBar:hover {
  background-color: #e4e5e6;
}

.panelToolsBar i {
  opacity: 0;
}

.panelToolsBar:hover i {
  opacity: 1;
}

.panelToolsBar:hover span {
  cursor: pointer;
  color: #409EFF;
}

.vue-grid-layout {
  background: rgb(244, 245, 245);
}
.vue-grid-item:not(.vue-grid-placeholder) {
  background: rgb(255, 255, 255);
  border: 2px solid #24292e1f;
  border-radius: 3px;
}
.vue-grid-item .static {
  background: #cce;
}

.back-button {
  padding: 0px;
  background-color: #f4f5f5;
  border: none;
}

.back-button:hover {
  background-color: #dadcdd;
}

.edit-area {
  display: flex;
  margin-top: 10px;
  margin-bottom: 10px;
  background-color: white;
  overflow-y: scroll;
  overflow-x: hidden;
  box-sizing: border-box;
}

.settings-label {
  font-weight: 500;
  font-size: 12px;
}

.settings-label:hover {
  cursor: default;
}

.el-form-subitem-wrapper {
  height: 30px;
  width: 125px;
  background-color: #f1f5f9;
  padding: 0px 6px;
  border: none;
  border-radius: 3px;
  display: flex;
  align-items: center;
}

.el-form-subitem-wrapper:hover {
  background-color: rgba(247, 247, 247);
  box-shadow: rgba(24, 26, 27, 0.2) 0px 1px 2px;
  cursor: default;
}

.radio-hidden {
  position: absolute;
  opacity: 0;
  z-index: -1000;
}

.radio-group {
  display: inline-flex;
  flex-flow: row nowrap;
  border: 1px solid rgba(36, 41, 46, 0.3);
  border-radius: 2px;
  padding: 2px;
}

.radio-label {
  display: inline-block;
  position: relative;
  font-size: 14px;
  height: 24px;
  line-height: 24px;
  color: rgba(36, 41, 46, 0.75);
  padding: 0px 8px;
  border-radius: 2px;
  background: rgb(255, 255, 255);
  cursor: pointer;
  z-index: 1;
  flex: 0 0 auto;
  text-align: center;
  user-select: none;
  white-space: nowrap;
}

.radio-hidden:checked + label {
  color: rgb(36, 41, 46);
  font-weight: 500;
  background: #dcdddd;
  z-index: 3;
}

.radio-hidden:hover + label {
  background-color: #d3d4d5;
}

.collapse-button {
  width: 36px;
  height: 32px;
  background-color: white;
  border: 1px solid rgba(36, 41, 46, 0.12);
  border-radius: 2px;
  color: rgba(36, 41, 46, 0.75);
  font-weight: 500;
}

.collapse-button:hover {
  background-color: #f4f5f5;
  border: 1px solid rgba(36, 41, 46, 0.12);
}

.collapse-item {
  border-top: 1px solid rgba(36, 41, 46, 0.12);
  display: flex;
  cursor: pointer;
  -webkit-box-align: baseline;
  align-items: center;
  padding: 4px;
  color: rgb(36, 41, 46);
  font-weight: 500;
}

.collapse-item-button {
  display: inline-flex;
  -webkit-box-align: center;
  align-items: center;
  font-size: 14px;
  font-weight: 500;
  font-family: Roboto, Helvetica, Arial, sans-serif;
  padding: 2px 2px;
  height: 26px;
  line-height: 30px;
  vertical-align: middle;
  cursor: pointer;
  border-radius: 2px;
  background: transparent;
  border: 1px solid transparent;
  transition: background-color 250ms cubic-bezier(0.4, 0, 0.2, 1) 0ms, color 250ms cubic-bezier(0.4, 0, 0.2, 1) 0ms;
  color: rgba(36, 41, 46, 0.75);
  margin-right: 4px;
}

.collapse-item-button:hover {
  background-color: #e7ebf2;
}

.collapse-item-text {
  -webkit-box-flex: 1;
  flex-grow: 1;
  overflow: hidden;
  line-height: 1.5;
  font-size: 14px;
  font-weight: 500;
  margin: 0px;
  color: rgb(36, 41, 46);
  letter-spacing: 0.01071em;
  font-family: Roboto, Helvetica, Arial, sans-serif;
}

.collapse-area {
  padding: 0px 10px 8px 32px;
}

.collapse-item-wrapper:hover {
  background-color: rgb(247, 247, 247);
}

.dropdown-menu-list {
  position: absolute;
  right: 0px;
  top: 100%;
  width: 100%;
  z-index: 1030;
  border: 1px solid rgba(36, 41, 46, 0.12);
  border-radius: 4px;
  box-shadow: rgba(24, 26, 27, 0.18) 0px 13px 20px 1px;
  background: white;
}

.dropdown-menu-list-item {
  cursor: pointer;
  flex: 1 1 0%;
  padding: 0;
  font-size: 14px;
}

.dropdown-menu-list-item:hover {
  background-color: #e4e5e6;
  cursor: pointer;
}

.dropdown-menu-list-item-button {
  padding: 8px 10px;
  border: none;
  width: 100%;
  display: flex;
  font-size: 14px;
  font-weight: 400;
}

.dropdown-menu-list-item-button:hover {
  background-color: #e4e5e6;
  font-weight: 500;
  color: rgb(36, 41, 46);
}

.workspace-item {
  display: grid;
  grid-auto-columns: 1fr;
  grid-auto-flow: row;
  grid-template: "Figure Heading Tags" 1fr
                 "Figure Meta Tags" auto
                 "Figure Description Tags" auto
                 "Figure Actions Secondary" auto / auto 1fr auto;
  margin-bottom: 6px;
  padding: 8px 16px;
  cursor: pointer;
  border-radius: 2px;
  background-color: rgb(244, 245, 245);
  width: 100%;
  min-height: 58px;
}

.workspace-item:hover {
  background-color: #eceded;
}

::v-deep .el-input__inner:focus {
  outline: transparent dotted 2px !important;
  outline-offset: 2px !important;
  box-shadow: rgb(244, 245, 245) 0px 0px 0px 2px, rgb(56, 113, 220) 0px 0px 0px 4px !important;
  transition-property: outline, outline-offset, box-shadow !important;
  transition-duration: 0.2s !important;
  transition-timing-function: cubic-bezier(0.19, 1, 0.22, 1) !important;
}

.primary-button {
  color: white;
  background-color: #3871dc;
  border: 1px solid transparent;
  border-radius: 4px;
  letter-spacing: 0.01071em;
  font-weight: 600;
  font-family: Roboto, Helvetica, Arial, sans-serif;
}

.primary-button:hover {
  background-color: #2c5ab0;
  color: white;
}

.danger-button {
  color: white;
  background-color: #e0226e;
  border: 1px solid transparent;
  border-radius: 4px;
  letter-spacing: 0.01071em;
  font-weight: 600;
  font-family: Roboto, Helvetica, Arial, sans-serif;
}

.danger-button:hover {
  background-color: #b31b58;
  color: white;
}

.divider-line {
  border-top: 1px solid rgba(36, 41, 46, 0.5);
  margin-top: 5px;
  margin-bottom: 20px;
  width: 100%;
}
</style>
